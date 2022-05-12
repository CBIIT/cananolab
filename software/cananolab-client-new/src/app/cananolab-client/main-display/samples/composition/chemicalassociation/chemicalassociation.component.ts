import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'canano-chemicalassociation',
  templateUrl: './chemicalassociation.component.html',
  styleUrls: ['../../../../../btn-bravo-canano.scss','./chemicalassociation.component.scss']
})
export class ChemicalassociationComponent implements OnInit {
  sampleId = Properties.CURRENT_SAMPLE_ID;
  dataId;
  sampleName = Properties.CURRENT_SAMPLE_NAME;
  helpUrl =  Consts.HELP_URL_SAMPLE_COMPOSITION;
  toolHeadingNameManage = 'Sample Composition';
  chemicalAssociationData;
  chemicalAssociationDataTrailer;
  nanomaterialEntityOptions;
  functionalizingEntityOptions;
  composingElementOptionsA;
  composingElementOptionsB;
  dataLoaded;
  entityOptionsA;
  entityOptionsB;
  setupData;
  currentDropdownValues;
  otherValue;
  currentField;
  errors;
    constructor( private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
    }


    ngOnInit(): void{
        this.currentDropdownValues = {};
        this.entityOptionsA = [];
        this.entityOptionsB = [];
        this.composingElementOptionsA = [];
        this.composingElementOptionsB = [];
        this.nanomaterialEntityOptions = [];
        this.functionalizingEntityOptions = [];

        this.chemicalAssociationData = this.setDefaultDataSet();
        this.chemicalAssociationDataTrailer = this.setDefaultDataSet();
      this.route.params.subscribe(
          ( params: Params ) => {
            this.sampleId = params['sampleId'];
            this.dataId = params['dataId'];

            if(
                  this.sampleId <= 0 ){
                  this.sampleId = Properties.CURRENT_SAMPLE_ID;
              }else{
                  Properties.CURRENT_SAMPLE_ID = this.sampleId;
              };
              if (this.dataId) {
                this.chemicalAssociationData = this.getChemicalAssociationData().subscribe(
                    data => {
                        Properties.SAMPLE_TOOLS = true;
                        this.chemicalAssociationData = data;
                        this.chemicalAssociationDataTrailer = JSON.parse(JSON.stringify(this.chemicalAssociationData));
                        Properties.CURRENT_SAMPLE_NAME = data['sampleName'];
                        this.loadSetupData();
                    } );
              }
              else {
                  this.loadSetupData();
              }



                }


      );
  }

  getChemicalAssociationData(){
    let getUrl = Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/edit?sampleId=' + this.sampleId + '&dataId=' + this.dataId;

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


setDefaultDataSet() {
    return {
        "sampleId":this.sampleId,
        "type":"",
        "bondType":"",
        "description":"",
        "associatedElementA":{
            "compositionType":"",
            "entityId":"",
            "entityDisplayName":"",
            "composingElement":{
                "id":""
            }
        },
        "associatedElementB":{
            "compositionType":"",
            "entityId":"",
            "entityDisplayName":"",
            "composingElement":{
                "id":""
            }
        }
    }
};


// set pointer fields to old values when adding other //
addOtherValue(field,currentValue) {
    this.currentDropdownValues[field]=currentValue;
    this.otherValue='';
    this.currentField=field;
};

// save other value //
saveOther(newItem: Object) {
    if (newItem['change'] && newItem['value']) {
        this.setupData[newItem['array']].push(newItem['value']);
        this.chemicalAssociationData.setValue(newItem['field'],newItem['value']);
    }
    else {
        this.chemicalAssociationData.setValue(newItem['field'],newItem['value']);
    }
};
changeEntityId(compositionType,entity, val) {
    // no need to do anything if functionalizing entity //
    if (entity=='nanomaterial entity') {
        if (compositionType==='compositionTypeA') {
            this.chemicalAssociationData.assoentityDisplayName
            let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/getComposingElementsByNanomaterialEntityId?id='+val,{});
            url.subscribe( data => {
                this.composingElementOptionsA=data;
            },
            err => {
                console.error( 'Error ', err );
            });
        }
        else {
            let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/getComposingElementsByNanomaterialEntityId?id='+val,{});
            url.subscribe( data => {
                this.composingElementOptionsB=data;
            },
            err => {
                console.error( 'Error ', err );
            });
        }
    };

    if (compositionType=='compositionTypeA') {
        this.entityOptionsA.forEach(element => {
            if (element.domainId==val) {
                this.chemicalAssociationData.associatedElementA.entityDisplayName=element.displayName;
            }
        });
    }
    else {
        this.entityOptionsB.forEach(element => {
            if (element.domainId==val) {
                this.chemicalAssociationData.associatedElementB.entityDisplayName=element.displayName;
            }
        });
    }
}

changeCompositionType(compositionType,val) {

    if (compositionType==='compositionTypeA') {
        this.entityOptionsA = val=='nanomaterial entity' ? this.nanomaterialEntityOptions:this.functionalizingEntityOptions;
    }
    else {
        this.entityOptionsB = val=='nanomaterial entity' ? this.nanomaterialEntityOptions:this.functionalizingEntityOptions;
    }
}

deleteChemicalAssociation() {
    if (confirm("Are you sure you want to delete this functionalizing entity?")) {
        let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/delete',this.chemicalAssociationData);
        url.subscribe( data => {
            this.router.navigate( ['home/samples/composition', this.sampleId] );
        },
        err => {
            console.error( 'Error ', err );
        });
    }
}

resetChemicalAssociation() {
    this.chemicalAssociationData = JSON.parse(JSON.stringify(this.chemicalAssociationDataTrailer));
}

submitChemicalAssociation() {
    let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/submit',this.chemicalAssociationData);
    url.subscribe( data => {
        this.router.navigate( ['home/samples/composition', this.sampleId] );
    },
    err => {
        console.error( 'Error ', err );
    });
}

selectAssociatedElement(entityId,domainId) {
    return entityId==domainId ? true:false;
}

loadDropdowns() {
    console.log(this.chemicalAssociationData.associatedElementA.compositionType,this.chemicalAssociationData.associatedElementA.entityId)
    this.changeEntityId('compositionTypeA',this.chemicalAssociationData.associatedElementA.compositionType,this.chemicalAssociationData.associatedElementA.entityId)
    this.changeEntityId('compositionTypeB',this.chemicalAssociationData.associatedElementB.compositionType,this.chemicalAssociationData.associatedElementB.entityId)
    this.changeCompositionType('compositionTypeA',this.chemicalAssociationData.associatedElementA.compositionType)
    this.changeCompositionType('compositionTypeB',this.chemicalAssociationData.associatedElementB.compositionType)

}

loadSetupData() {
    let getUrl = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/setup?sampleId=' + this.sampleId);
    getUrl.subscribe( data => {
        this.setupData = data;
        let nanoUrl = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/getAssociatedElementOptions?compositionType=nanomaterial entity',{});
        nanoUrl.subscribe( data => {
            let functionalizingUrl = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/getAssociatedElementOptions?compositionType=functionalizing entity',{});
            this.nanomaterialEntityOptions=data;
            console.log(data)
            functionalizingUrl.subscribe( data => {
                this.functionalizingEntityOptions=data;
                if (this.dataId) {
                    this.loadDropdowns();
                }

            },
            err => {
                console.error( 'Error ', err );
            });
        },
        err => {
            console.error( 'Error ', err );
        });
    },
    err => {
        console.error( 'Error ', err );
    });
}




}
