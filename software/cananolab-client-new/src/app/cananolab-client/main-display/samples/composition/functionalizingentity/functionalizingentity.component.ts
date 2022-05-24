import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';
import { NavigationService } from '../../../../common/services/navigation.service';
import { ApiService } from '../../../../common/services/api.service';
@Component({
  selector: 'canano-functionalizingentity',
  templateUrl: './functionalizingentity.component.html',
  styleUrls: ['../../../../../btn-bravo-canano.scss','./functionalizingentity.component.scss']
})

export class FunctionalizingentityComponent implements OnInit {
    // currentDropdownValue is the previous dropdown value selected //
    // This is used to reset the dropdown if other is selected and canceled //
    currentDropdownValues;
    dataId;
    error;
    inherentFunction;

    // use these instead to determine open or closed form //
    inherentFunctionIndex;
    //targetIndex;

    inherentFunctionTrailer;
    data;
    dataTrailer;
    fileIndex;
    helpUrl =  Consts.HELP_URL_SAMPLE_COMPOSITION;
    sampleName = Properties.CURRENT_SAMPLE_NAME;
    sampleId;
    setupData;
    toolHeadingNameManage = 'Sample Composition';
    targets;
    target;
    targetCopy;
    targetIndex;

  constructor( private apiService:ApiService,private navigationService:NavigationService,private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
  }

  ngOnInit(): void{
      this.navigationService.setCurrentSelectedItem(1);
    this.route.params.subscribe(
        ( params: Params ) => {
          this.sampleId = params['sampleId'];
          this.dataId = params['dataId'];
          this.currentDropdownValues = {};
          this.data = this.setDefaultDataSet();
          this.dataTrailer = this.setDefaultDataSet();
          if(
                this.sampleId <= 0 ){
                    this.sampleId = Properties.CURRENT_SAMPLE_ID;
            }else{
                Properties.CURRENT_SAMPLE_ID = this.sampleId;
            };
            this.apiService.getSampleName(this.sampleId).subscribe(
                data=>this.toolHeadingNameManage='Sample ' +data['sampleName'] + ' Functionalizing Entity'
            )
            if (this.dataId) {
                this.data = this.getdata(this.sampleId).subscribe(
                    data => {
                        Properties.SAMPLE_TOOLS = true;
                        this.data = data;
                        this.dataTrailer = JSON.parse(JSON.stringify(this.data));
                        Properties.CURRENT_SAMPLE_NAME = data['sampleName'];
                    } );
            };

            this.setupData = this.getSetupData(this.sampleId).subscribe(
                data => {
                    Properties.SAMPLE_TOOLS = true;
                    this.setupData = data;
                } );
        }
    );
}

addFile() {
    this.fileIndex=-1;
}

// cancels inherent function //
cancelInherentFunction() {
    this.inherentFunctionIndex=null;
};

// cancels target form //
cancelTarget() {
    if (this.targetIndex>=0) {
        this.inherentFunction.targets[this.targetIndex]=this.targetCopy;
    }
    this.targetIndex=null;
};

// deletes current inherent function //
deleteInherentFunction() {
    if (confirm("Are you sure you want to delete this inherent function?")) {
        this.data['simpleFunctionBean']=this.inherentFunction;
        let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/functionalizingEntity/removeFunction', this.data );
        url.subscribe( data => {
            this.data=data;
            this.dataTrailer = JSON.parse(JSON.stringify(this.data));
        },
        err => {
            console.error( 'Error ', err );
        });
        this.inherentFunctionIndex=null;
        this.targetIndex=null;
    };
};

// deletes current target //
deleteTarget() {
    if (confirm("Are you sure you want to delete this target?")) {
        this.inherentFunction.targets.splice(this.targetIndex,1);
        this.target=null;
        this.targetIndex=null;
    };
};

// brings up add inherent function form //
addInherentFunction() {
    this.inherentFunctionIndex=-1;
    this.inherentFunction = {
        "type":"","modality":"",
        "description":"",
        "targetId":"",
        "targetType":"",
        "speciesType":"",
        "targetName":"",
        "targetDescription":"",
        "targets":[]};
};

// brings up edit inherent function form //
editInherentFunction(inherentFunction,index) {
    this.inherentFunctionIndex=index;
    this.target = null;
    this.targetIndex = null;
    this.inherentFunction = JSON.parse(JSON.stringify(inherentFunction));
    this.inherentFunctionTrailer = JSON.parse(JSON.stringify(inherentFunction));

};

// brings up add target form //
addTarget() {
    this.targetIndex=-1;
    this.target = {
        "type":"",
        "description":"",
        "name":"",
        "species":""
    };
};

// brings up edit target form //
editTarget(target,index) {
    this.targetIndex=parseInt(index);
    this.target = target;
    this.target['species']='';
    this.targetCopy = JSON.parse(JSON.stringify(target));
};

// saves curent inherent function //
saveInherentFunction() {
    this.data['simpleFunctionBean']=this.inherentFunction;
    let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/functionalizingEntity/saveFunction', this.data );
    url.subscribe( data => {
        this.data=data;
        this.dataTrailer = JSON.parse(JSON.stringify(this.data));

    },
    err => {
        console.error( 'Error ', err );
    });
    this.inherentFunction=null;
    this.target=null;
    this.targetIndex=null;
    this.inherentFunctionIndex=null;
};

// saves current target //
saveTarget() {
    if (this.targetIndex==-1) {
        this.inherentFunction.targets.push(this.target);
    };
    this.targetIndex=null;
};

// gets all data for current functionalizing entity //
getdata(sampleId){
    let getUrl = Properties.API_SERVER_URL + '/caNanoLab/rest/functionalizingEntity/edit?sampleId=' + sampleId + '&dataId=' + this.dataId;

    if( Properties.DEBUG_CURL ){
        let curl = 'curl  -k \'' + getUrl + '\'';
    }

    let headers = new HttpHeaders( {
        'Content-Type': 'application/x-www-form-urlencoded'
    } );

    let options = {
        headers: headers,
        method: 'get',
    };

    let results;
    try{
        results = this.httpClient.get( getUrl, options ).pipe( timeout( Properties.HTTP_TIMEOUT ) );
    }catch( e ){
        // TODO react to error.
        console.error( 'doGet Exception: ' + e );
    }
    return results;

}

    // gets dropdown data //
    getSetupData(sampleId){
        let getUrl = Properties.API_SERVER_URL + '/caNanoLab/rest/functionalizingEntity/setup?sampleId=' + sampleId;

        if( Properties.DEBUG_CURL ){
            let curl = 'curl  -k \'' + getUrl + '\'';
        }

        let headers = new HttpHeaders( {
            'Content-Type': 'application/x-www-form-urlencoded'
        } );

        let options = {
            headers: headers,
            method: 'get',
        };

        let results;
        try{
            results = this.httpClient.get( getUrl, options ).pipe( timeout( Properties.HTTP_TIMEOUT ) );
        }catch( e ){
            // TODO react to error.
            console.error( 'doGet Exception: ' + e );
        }
        return results;

    };

    // set pointer fields to old values when adding other //
    addOtherValue(field,currentValue) {
        this.currentDropdownValues[field]=currentValue;
    };

    // save other value //
    saveOther(newItem: Object) {
        if (newItem['change'] && newItem['value']) {
            this.addDropdownItem(newItem['array'],newItem['value']);
            this.setValue(newItem['field'],newItem['value']);
        }
        else {
            this.setValue(newItem['field'],newItem['value']);
        }
    };

    // sets default data set for functionalizing entity for add //
    setDefaultDataSet() {
        return {
            "sampleId":this.sampleId,
            "type":"",
            "pubChemDataSourceName":"",
            "molecularFormulaType":"",
            "activationMethodType":"",
            "domainEntity":{
                "type":"",
                "isoType":"",
                "species":""
            },
            "valueUnit":"",
            "name":"",
            "functionList":[],
            "fileList":[]
        };
    };

    // deletes current functionalizing entity //
    deleteFunctionalizingEntity() {
        if (confirm("Are you sure you want to delete this functionalizing entity?")) {
            let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/functionalizingEntity/delete', this.data );
            url.subscribe( data => {
                this.router.navigate( ['home/samples/composition', this.sampleId] );
            },
            err => {
                console.error( 'Error ', err );
            });
        };
    };

    // resets functionalizing entity form //
    resetFunctionalizingEntity() {
        this.data = JSON.parse(JSON.stringify(this.dataTrailer))
    };

    // saves functionalizing entity //
    submitFunctionalizingEntity() {
        let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/functionalizingEntity/submit', this.data );
        url.subscribe( data => {
            this.router.navigate( ['home/samples/composition', this.sampleId] );

        },
        err => {
            console.error( 'Error ', err );
        });
    };

    changeFile(newItem:Object) {
        console.log(newItem)
        if (newItem['type']=='save') {
            this.data=newItem['data'];
            this.dataTrailer = JSON.parse(JSON.stringify(this.data));

        }
    }

}
