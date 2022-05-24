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
  data;
  dataTrailer;
  fileIndex;
  nanomaterialEntityOptions;
  functionalizingEntityOptions;
  composingElementOptionsA;
  composingElementOptionsB;
  dataLoaded;
  entityOptionsA;
  entityOptionsB;
  setupData;
  currentDropdownValues;
  currentField;
  errors;
    constructor( private apiService:ApiService,private navigationService:NavigationService,private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
    }


    ngOnInit(): void{
        this.navigationService.setCurrentSelectedItem(1);
        this.currentDropdownValues = {};
        this.entityOptionsA = [];
        this.entityOptionsB = [];
        this.composingElementOptionsA = [];
        this.composingElementOptionsB = [];
        this.nanomaterialEntityOptions = [];
        this.functionalizingEntityOptions = [];

        this.data = this.setDefaultDataSet();
        this.dataTrailer = this.setDefaultDataSet();
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
              this.apiService.getSampleName(this.sampleId).subscribe(
                data=>this.toolHeadingNameManage='Sample ' +data['sampleName'] + ' Chemical Association'
            )
              if (this.dataId) {
                this.data = this.getdata().subscribe(
                    data => {
                        Properties.SAMPLE_TOOLS = true;
                        this.data = data;
                        this.dataTrailer = JSON.parse(JSON.stringify(this.data));
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

  getdata(){
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

isSubmissionValid() {
    if (this.data.type=='') {
        return true
    }
    if (this.data.type=='attachment'&&this.data.bondType=='') {
        return true
    }
    let AKeys=Object.keys(this.data['associatedElementA']);
    let BKeys=Object.keys(this.data['associatedElementB']);
    let ACount=[];
    let BCount=[];

    AKeys.forEach(item=> {
        if (this.data['associatedElementA'][item]=='') {
            ACount.push(item)
        }
        if (this.data['associatedElementA']['compositionType']=='nanomaterial entity') {
            if (this.data['associatedElementA']['composingElement']['id']=='') {
                ACount.push(item)
            }
        }
    })
    BKeys.forEach(item=> {
        if (this.data['associatedElementB'][item]=='') {
            BCount.push(item)
        }
        if (this.data['associatedElementB']['compositionType']=='nanomaterial entity') {
            if (this.data['associatedElementB']['composingElement']['id']=='') {
                BCount.push(item)
            }
        }
    })
    if (ACount.length||BCount.length) {
        return true
    }
    return false
}
setDefaultDataSet() {
    return {
        "sampleId":this.sampleId,
        "type":"",
        "bondType":"",
        "description":"",
        "files":[],
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

changeEntityId(compositionType,entity, val) {
    // no need to do anything if functionalizing entity //
    if (entity=='nanomaterial entity') {
        if (compositionType==='compositionTypeA') {
            this.data.assoentityDisplayName
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
                this.data.associatedElementA.entityDisplayName=element.displayName;
            }
        });
    }
    else {
        this.entityOptionsB.forEach(element => {
            if (element.domainId==val) {
                this.data.associatedElementB.entityDisplayName=element.displayName;
            }
        });
    }
}

changeCompositionType(compositionType,val,edit) {

    if (compositionType==='compositionTypeA') {
        this.entityOptionsA = val=='nanomaterial entity' ? this.nanomaterialEntityOptions:this.functionalizingEntityOptions;
        if (!edit) {
            this.data['associatedElementA']['entityId']=''
            this.data['associatedElementA']['entityDisplayName']=''
            this.data['associatedElementA']['composingElement']={'id':''}
        }

    }
    else {
        this.entityOptionsB = val=='nanomaterial entity' ? this.nanomaterialEntityOptions:this.functionalizingEntityOptions;
        if (!edit) {
            this.data['associatedElementB']['entityId']=''
            this.data['associatedElementB']['entityDisplayName']=''
            this.data['associatedElementB']['composingElement']={'id':''}
        }

    }
}

deleteChemicalAssociation() {
    if (confirm("Are you sure you want to delete this functionalizing entity?")) {
        let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/delete',this.data);
        url.subscribe( data => {
            this.router.navigate( ['home/samples/composition', this.sampleId] );
        },
        err => {
            console.error( 'Error ', err );
        });
    }
}

resetChemicalAssociation() {
    this.data = JSON.parse(JSON.stringify(this.dataTrailer));
}

submitChemicalAssociation() {
    let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/submit',this.data);
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
    this.changeEntityId('compositionTypeA',this.data.associatedElementA.compositionType,this.data.associatedElementA.entityId)
    this.changeEntityId('compositionTypeB',this.data.associatedElementB.compositionType,this.data.associatedElementB.entityId)
    this.changeCompositionType('compositionTypeA',this.data.associatedElementA.compositionType,true)
    this.changeCompositionType('compositionTypeB',this.data.associatedElementB.compositionType,true)

}

loadSetupData() {
    let getUrl = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/setup?sampleId=' + this.sampleId);
    getUrl.subscribe( data => {
        this.setupData = data;
        let nanoUrl = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/getAssociatedElementOptions?compositionType=nanomaterial entity',{});
        nanoUrl.subscribe( data => {
            let functionalizingUrl = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/getAssociatedElementOptions?compositionType=functionalizing entity',{});
            this.nanomaterialEntityOptions=data;
            functionalizingUrl.subscribe( data => {
                this.functionalizingEntityOptions=data;
                if (this.dataId) {
                    this.loadDropdowns();
                }
                Properties.SAMPLE_TOOLS = true;

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

changeFile(newItem:Object) {
    if (newItem['type']=='save'||newItem['type']=='delete') {
        this.data=newItem['data'];
        this.dataTrailer = JSON.parse(JSON.stringify(this.data));


    }
}




}
