import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'canano-functionalizingentity',
  templateUrl: './functionalizingentity.component.html',
  styleUrls: ['../../../../../btn-bravo-canano.scss','./functionalizingentity.component.scss']
})

export class FunctionalizingentityComponent implements OnInit {
    // currentDropdownValue is the previous dropdown value selected //
    // This is used to reset the dropdown if other is selected and canceled //
    currentDropdownValues;
    // currentField is the current dropdown field on the form //
    // This is used to reset the dropdown if other is selected and canceled //
    currentField;
    dataId;
    error;
    inherentFunction;

    // use these instead to determine open or closed form //
    inherentFunctionIndex;
    //targetIndex;

    inherentFunctionTrailer;
    functionalizingEntityData;
    functionalizingEntityDataTrailer;
    helpUrl =  Consts.HELP_URL_SAMPLE_COMPOSITION;
    otherValue;
    sampleName = Properties.CURRENT_SAMPLE_NAME;
    sampleId;
    setupData;
    toolHeadingNameManage = 'Sample Composition';
    targets;
    target;
    targetCopy;
    targetIndex;

  constructor( private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
  }

  ngOnInit(): void{
    this.route.params.subscribe(
        ( params: Params ) => {
          this.sampleId = params['sampleId'];
          this.dataId = params['dataId'];
          this.currentDropdownValues = {};
          this.functionalizingEntityData = this.setDefaultDataSet();
          this.functionalizingEntityDataTrailer = this.setDefaultDataSet();
          if(
                this.sampleId <= 0 ){
                    this.sampleId = Properties.CURRENT_SAMPLE_ID;
            }else{
                Properties.CURRENT_SAMPLE_ID = this.sampleId;
            };
            if (this.dataId) {
                this.functionalizingEntityData = this.getFunctionalizingEntityData(this.sampleId).subscribe(
                    data => {
                        Properties.SAMPLE_TOOLS = true;
                        this.functionalizingEntityData = data;
                        this.functionalizingEntityDataTrailer = JSON.parse(JSON.stringify(this.functionalizingEntityData));
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

cancelInherentFunction() {
    this.inherentFunctionIndex=null;
};

cancelTarget() {
    if (this.targetIndex>=0) {
        this.inherentFunction.targets[this.targetIndex]=this.targetCopy;
    }
    this.targetIndex=null;
};

deleteInherentFunction() {
    if (confirm("Are you sure you want to delete this inherent function?")) {
        this.functionalizingEntityData['simpleFunctionBean']=this.inherentFunction;
        let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/functionalizingEntity/removeFunction', this.functionalizingEntityData );
        url.subscribe( data => {
            this.functionalizingEntityData=data;
            this.functionalizingEntityDataTrailer = JSON.parse(JSON.stringify(this.functionalizingEntityData));
        },
        err => {
            console.error( 'Error ', err );
        });
        this.inherentFunctionIndex=null;
        this.targetIndex=null;
    };
};

deleteTarget() {
    if (confirm("Are you sure you want to delete this target?")) {
        this.inherentFunction.targets.splice(this.targetIndex,1);
        this.target=null;
        this.targetIndex=null;
    };
};

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

editInherentFunction(inherentFunction,index) {
    this.inherentFunctionIndex=index;
    this.target = null;
    this.targetIndex = null;
    this.inherentFunction = JSON.parse(JSON.stringify(inherentFunction));
    this.inherentFunctionTrailer = JSON.parse(JSON.stringify(inherentFunction));

};

addTarget() {
    this.targetIndex=-1;
    this.target = {
        "type":"",
        "description":"",
        "name":"",
        "species":""
    };
};

editTarget(target,index) {
    this.targetIndex=parseInt(index);
    this.target = target;
    this.target['species']='';
    this.targetCopy = JSON.parse(JSON.stringify(target));
};

saveInherentFunction() {
    this.functionalizingEntityData['simpleFunctionBean']=this.inherentFunction;
    let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/functionalizingEntity/saveFunction', this.functionalizingEntityData );
    url.subscribe( data => {
        this.functionalizingEntityData=data;
        this.functionalizingEntityDataTrailer = JSON.parse(JSON.stringify(this.functionalizingEntityData));

    },
    err => {
        console.error( 'Error ', err );
    });
    this.inherentFunction=null;
    this.target=null;
    this.targetIndex=null;
    this.inherentFunctionIndex=null;
};

saveTarget() {
    if (this.targetIndex==-1) {
        this.inherentFunction.targets.push(this.target);
    };
    this.targetIndex=null;
};

getFunctionalizingEntityData(sampleId){
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
        this.otherValue='';
        this.currentField=field;
    };

    // save functionalizing entity //
    saveFunctionalizingEntity() {

    };

    // save other value //
    saveOther(newItem: Object) {
        if (newItem['change'] && newItem['value']) {
            this.setupData[newItem['array']].push(newItem['value']);
            this.functionalizingEntityData.setValue(newItem['field'],newItem['value']);
        }
        else {
            this.functionalizingEntityData.setValue(newItem['field'],newItem['value']);
        }
    };

    setDefaultDataSet() {
        console.log(this.sampleId)
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
            "functionList":[]
        };
    };

    deleteFunctionalizingEntity() {
        if (confirm("Are you sure you want to delete this functionalizing entity?")) {
            let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/functionalizingEntity/delete', this.functionalizingEntityData );
            url.subscribe( data => {
                this.router.navigate( ['home/samples/composition', this.sampleId] );
            },
            err => {
                console.error( 'Error ', err );
            });
        };
    };

    resetFunctionalizingEntity() {
        this.functionalizingEntityData = JSON.parse(JSON.stringify(this.functionalizingEntityDataTrailer))
    };

    submitFunctionalizingEntity() {
        let url = this.httpClient.post( Properties.API_SERVER_URL + '/caNanoLab/rest/functionalizingEntity/submit', this.functionalizingEntityData );
        url.subscribe( data => {
            this.router.navigate( ['home/samples/composition', this.sampleId] );

        },
        err => {
            console.error( 'Error ', err );
        });
    };

}
