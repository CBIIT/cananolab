import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/cananolab-client/common/services/api.service';
import { Consts } from 'src/app/constants';
import { AdvancedSearchService } from './advanced-search.service';
import { Router } from '@angular/router';
@Component({
  selector: 'canano-advanced-search',
  templateUrl: './advanced-search.component.html',
  styleUrls: ['./advanced-search.component.scss']
})
export class AdvancedSearchComponent implements OnInit {

    constructor(
        private router:Router,
        private advancedSearchService:AdvancedSearchService,
        private apiService:ApiService) { }

        characterizationEditIndex;
        characterizationQuery;
        characterizationQuery2;
        compositionEditIndex;
        compositionEntityTypes=[];
        compositionQuery;
        errors={};
        query={};
        sampleEditIndex;
        sampleQuery;
        setupData={};

    ngOnInit(): void {
        this.apiService.doGet(Consts.QUERY_SAMPLE_ADVANCED_SEARCH_SETUP,'').subscribe(data=> {
            this.setupData=data;
            this.setupData['characterizationDatumNames']=[];
            this.setupData['characterizationDatumValueUnits']=[];
            this.setupData['characterizationNames']=[];
            this.setupData['compositionEntityTypes']=[];
            this.setupData['loaded']=true;
            });
        this.setupQueries(); // sets up main query //
    }

    changeCompositionType(event) {
        if (event=='nanomaterial entity') this.setupData['compositionEntityTypes']=this.setupData['nanomaterialEntityTypes'];
        if (event=='functionalizing entity') this.setupData['compositionEntityTypes']=this.setupData['functionalizingEntityTypes'];
        if (event=='function') this.setupData['compositionEntityTypes']=this.setupData['functionTypes'];
    }

    changeCharacterizationType(event,currentVal) {
        if (!currentVal) this.setupCharacterizationQuery();
        this.setupData['characterizationDatumNames']=[];
        this.setupData['characterizationDatumValueUnits']=[];
        this.characterizationQuery['characterizationType']=event;
        this.apiService.doGet(Consts.QUERY_SAMPLE_GET_CHARACTERIZATION_OPTIONS,'charType='+event).subscribe(data=> {
            this.setupData['characterizationNames']=data;
        },
        errors=> {
            this.errors=errors;
        })
    }

    changeCharacterizationName(event) {
        this.apiService.doGet(Consts.QUERY_SAMPLE_GET_DATUM_OPTIONS,'charType='+this.characterizationQuery.characterizationType+'&charName='+event).subscribe(data=> {
            this.setupData['characterizationDatumNames']=data;
            if (this.characterizationEditIndex==null) {
                this.characterizationQuery['datumValueUnit']='';
            }
        },
        errors=> {
            this.errors=errors;
        })
    }

    changeCharacterizationDatumName(event) {
        this.apiService.doGet(Consts.QUERY_SAMPLE_GET_DATUM_UNIT_OPTIONS,'datumName='+event).subscribe(data=> {
            this.setupData['characterizationDatumValueUnits']=data;
            if (this.characterizationEditIndex==null) {
                this.characterizationQuery['datumValueUnit']='';
            }
        },
        errors=> {
            this.errors=errors;
        })
    }

    // sample methods //
    cancelSampleQuery() {
        this.sampleEditIndex=null;
        this.setupSampleQuery();
    }

    deleteSampleQuery() {
        if (confirm("Are you sure you wish to delete this sample query?")) {
            this.query['sampleQueries'].splice(this.sampleEditIndex,1)
            this.sampleEditIndex=null;
            this.setupSampleQuery();
        }
    }

    editSampleQuery(index) {
        this.sampleEditIndex=index;
        this.sampleQuery=JSON.parse(JSON.stringify(this.query['sampleQueries'][index]));
    }

    saveSampleQuery() {
        Object.keys(this.sampleQuery).forEach(item=> {
            if (this.sampleQuery[item]=='') delete this.sampleQuery[item];
        })
        if (this.sampleEditIndex!=null) {
            this.query['sampleQueries'][this.sampleEditIndex]=this.sampleQuery;
        }
        else {
            this.query['sampleQueries'].push(this.sampleQuery);
        }
        this.sampleEditIndex=null;
        this.setupSampleQuery();
    }
    // end sample methods

    // composition methods //

    cancelCompositionQuery() {
        this.compositionEditIndex=null;
        this.setupCompositionQuery();
    }

    deleteCompositionQuery() {
        if (confirm("Are you sure you wish to delete this composition query?")) {
            this.query['compositionQueries'].splice(this.compositionEditIndex,1)
            this.compositionEditIndex=null;
            this.setupCompositionQuery();
            this.setupData['compositionEntityTypes']=[];
        }
    }

    editCompositionQuery(index) {
        this.compositionEditIndex=index;
        this.compositionQuery=JSON.parse(JSON.stringify(this.query['compositionQueries'][index]));
        this.changeCompositionType(this.compositionQuery.compositionType);
    }

    saveCompositionQuery() {
        Object.keys(this.compositionQuery).forEach(item=> {
            if (this.compositionQuery[item]=='') delete this.compositionQuery[item];
        })
        if (this.compositionEditIndex!=null) {
            this.query['compositionQueries'][this.compositionEditIndex]=this.compositionQuery;
        }
        else {
            this.query['compositionQueries'].push(this.compositionQuery);
        }
        this.compositionEditIndex=null;
        this.setupCompositionQuery();
        this.setupData['compositionEntityTypes']=[];
    }
    // end composition methods //

    // characterization methods //

    cancelCharacterizationQuery() {
        this.characterizationEditIndex=null;
        this.setupCharacterizationQuery();
        this.setupData['characterizationNames']=[];
        this.setupData['characterizationDatumNames']=[];
        this.setupData['characterizationDatumValueUnits']=[];
    }

    deleteCharacterizationQuery() {
        if (confirm("Are you sure you wish to delete this characterization query?")) {
            this.query['characterizationQueries'].splice(this.characterizationQuery,1)
            this.characterizationEditIndex=null;
            this.setupCharacterizationQuery();
            this.setupData['characterizationNames']=[];
            this.setupData['characterizationDatumNames']=[];
            this.setupData['characterizationDatumValueUnits']=[];
        }
    }

    editCharacterizationQuery(index) {
        this.characterizationEditIndex=index;
        this.characterizationQuery=JSON.parse(JSON.stringify(this.query['characterizationQueries'][index]));
        this.characterizationQuery2=JSON.parse(JSON.stringify(this.query['characterizationQueries'][index]));
        this.changeCharacterizationType(this.characterizationQuery.characterizationType,1);
        this.changeCharacterizationName(this.characterizationQuery.characterizationName);
        this.changeCharacterizationDatumName(this.characterizationQuery.datumName);
    }

    saveCharacterizationQuery() {
        Object.keys(this.characterizationQuery).forEach(item=> {
            if (this.characterizationQuery[item]=='') delete this.characterizationQuery[item];
        })
        if (this.characterizationEditIndex!=null) {
            this.query['characterizationQueries'][this.characterizationEditIndex]=this.characterizationQuery;
        }
        else {
            this.query['characterizationQueries'].push(this.characterizationQuery);
        }
        this.characterizationEditIndex=null;
        this.setupCharacterizationQuery();
        this.setupData['characterizationNames']=[];
        this.setupData['characterizationDatumNames']=[];
        this.setupData['characterizationDatumValueUnits']=[];
    }
    // end characterization methods //

    isCharacterizationAddValid() {
        let validity=true;
        let keys=['characterizationType','characterizationName'];
        keys.forEach(key=> {
            if (this.characterizationQuery[key]=='') {
                validity=false;
            };
        })
        return validity;
    };

    isCompositionAddValid() {
        let validity=true;
        let keys=['compositionType','entityType'];
        keys.forEach(key=> {
            if (this.compositionQuery[key]=='') {
                validity=false;
            };
        })
        return validity;
    };

    isSampleAddValid() {
        let validity=true;
        Object.keys(this.sampleQuery).forEach(key=> {
            if (this.sampleQuery[key]=='') {
                validity=false;
            };
        })
        return validity;
    };

    reset() {
        this.setupCharacterizationQuery();
        this.setupCompositionQuery();
        this.setupSampleQuery();
        this.query['characterizationQueries']=[];
        this.query['compositionQueries']=[];
        this.query['sampleQueries']=[];
        this.setupData['characterizationDatumNames']=[];
        this.setupData['characterizationDatumValueUnits']=[];
        this.setupData['characterizationNames']=[];
        this.setupData['compositionEntityTypes']=[];
    }

    resetSampleQueries() {
        this.query['sampleQueries']=[];
        this.setupSampleQuery();
    }

    resetCompositionQueries() {
        this.query['compositionQueries']=[];
        this.setupData['compositionEntityTypes']=[];
        this.setupCompositionQuery();
    }

    resetCharacterizationQueries() {
        this.query['characterizationQueries']=[];
        this.setupData['characterizationNames']=[];
        this.setupData['characterizationDatumNames']=[];
        this.setupData['characterizationDatumValueUnits']=[];
        this.setupCharacterizationQuery();
    }
    search() {
        this.apiService.doPost(Consts.QUERY_SAMPLE_ADVANCED_SEARCH,this.query).subscribe(data=> {
            this.advancedSearchService.setAdvancedSearchResults(data);
            this.router.navigate(['home/samples/sample-advanced-search-results'])
        },
        error=> {
            this.errors=error;
        })
    }
    setupQueries() {
        this.query={
            characterizationLogicalOperator:"and",
            characterizationQueries:[],
            compositionLogicalOperator:"and",
            compositionQueries:[],
            logicalOperator:"and",
            sampleLogicalOperator:"and",
            sampleQueries:[]
        };

        this.setupCharacterizationQuery();
        this.setupCompositionQuery();
        this.setupSampleQuery();

    }

    setupCharacterizationQuery() {
        this.characterizationQuery={
            characterizationName: "",
            characterizationType: "",
            datumName: "",
            datumValue: "",
            datumValueUnit: "",
            operand: "",
            type: "CharacterizationQueryBean"}
    }

    setupCompositionQuery() {
        this.compositionQuery={
            chemicalName: "",
            compositionType: "",
            entityType: "",
            operand: "",
            type: "CompositionQueryBean"
        };
    }

    setupSampleQuery() {
        this.sampleQuery={
            nameType:"",
            operand:"",
            name:"",
            type:"SampleQueryBean"
        }
    }
}
