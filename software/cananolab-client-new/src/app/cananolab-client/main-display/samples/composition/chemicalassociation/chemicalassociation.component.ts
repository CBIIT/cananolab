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
  setupData;
  currentDropdownValues;
  otherValue;
  currentField;

    constructor( private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
    }


    ngOnInit(): void{
      this.route.params.subscribe(
          ( params: Params ) => {
            this.sampleId = params['sampleId'];
            this.dataId = params['dataId'];
            this.chemicalAssociationData = this.setDefaultDataSet();
            this.chemicalAssociationDataTrailer = this.setDefaultDataSet();
            if(
                  this.sampleId <= 0 ){
                  this.sampleId = Properties.CURRENT_SAMPLE_ID;
              }else{
                  Properties.CURRENT_SAMPLE_ID = this.sampleId;
              };
              this.chemicalAssociationData = this.getChemicalAssociationData().subscribe(
                data => {
                    console.log(data)
                    Properties.SAMPLE_TOOLS = true;
                    this.chemicalAssociationData = data;
                    this.chemicalAssociationDataTrailer = JSON.parse(JSON.stringify(this.chemicalAssociationData));
                    Properties.CURRENT_SAMPLE_NAME = data['sampleName'];
                } );
                this.setupData = this.getSetupData().subscribe(
                    data => {
                        Properties.SAMPLE_TOOLS = true;
                        this.setupData = data;
                    } );
          }
      );
  }

  getChemicalAssociationData(){
    let getUrl = Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/edit?sampleId=' + this.sampleId + '&dataId=' + this.dataId;

    if( Properties.DEBUG_CURL ){
        let curl = 'curl  -k \'' + getUrl + '\'';
        console.log( curl );
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

getSetupData(){
    let getUrl = Properties.API_SERVER_URL + '/caNanoLab/rest/chemicalAssociation/setup?sampleId=' + this.sampleId;

    if( Properties.DEBUG_CURL ){
        let curl = 'curl  -k \'' + getUrl + '\'';
        console.log( curl );
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
        "type":"",
        "bondType":"",
        "description":""
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

}
