import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Consts } from '../../../../constants';
import { ApiService } from '../../../common/services/api.service';
import { UtilService } from '../../../common/services/util.service';
import { Router } from '@angular/router';
import { SampleSearchResultsService } from './sample-search-results/sample-search-results.service';

@Component({
  selector: 'canano-sample-search',
  templateUrl: './sample-search.component.html',
  styleUrls: ['./sample-search.component.scss']
})
export class SampleSearchComponent implements OnInit {
    @ViewChild( 'f', { static: false } ) sampleSearchForm: NgForm;
    helpUrl = Consts.HELP_URL_SAMPLE_SEARCH;
    toolHeadingNameSearchSample = 'Sample Search';
    keywords;
    sampleSetupData = {};
    pocOperand = 'contains';
    nameOperand = 'contains';
    sampleName;
    samplePointOfContact;
    characterizationType; // Note - not plural (no s)
    nanomaterialEntityTypes = [];
    functionTypes = [];
    functionalizingEntityTypes = [];
    characterizationsList = [];
    characterizations = [];
    searchResults;

// QUERY_SAMPLE_SETUP

    constructor( private apiService: ApiService, private utilService: UtilService,
                 private router: Router, private sampleSearchResultsService: SampleSearchResultsService){
    }

    ngOnInit(): void{
        this.init();
    }

    init(){

        this.apiService.doGet( Consts.QUERY_SAMPLE_SETUP, '' ).subscribe(
            data => {
                this.sampleSetupData = data;
                console.log( 'MHL gorp 00 QUERY_SAMPLE_SETUP data: ', this.sampleSetupData );
            } );
    }

    onCharacterizationType(charType){
        console.log('MHL onCharacterizationType: ', charType);
        this.characterizationType = charType;
        // QUERY_GET_CHARACTERIZATION_BY_TYPE
        this.apiService.doGet( Consts.QUERY_GET_CHARACTERIZATION_BY_TYPE, 'type=' + charType ).subscribe(
            data => {
                this.characterizationsList = <any>data;
                console.log( 'MHL  00 onCharacterizationType characterizationsList: ', this.characterizationsList );
            } );

    }

    onSubmit(){
        console.log( 'MHL SamplesComponent Search: ', this.sampleSearchForm.form.value );
        console.log('MHL buildParameterString: ', this.buildParameterString());

        // QUERY_SEARCH_SAMPLE
        this.apiService.doPost( Consts.QUERY_SEARCH_SAMPLE, this.buildParameterString() ).subscribe(
            data => {
                this.searchResults = <any>data;
                console.log( 'MHL  00 onCharacterizationType characterizationsList: ', this.searchResults );
               // alert( 'Got back ' + this.searchResults.length + ' results.');

                // send search results to samplesSearchResults
                this.sampleSearchResultsService.setSearchResults( this.searchResults );
                this.router.navigate(['home/samples/samplesSearchResults']); // @FIMENOW TESTING  Don't hard code this!!!
            } );
    }


    buildParameterString(): string{
        // Add the parts that are not from the UI form
        let parameters = '';

        // Put the data from the form into "parameters"
        Object.keys( this.sampleSearchForm.value )
            .forEach( key => {
                    console.log( 'MHL Key: ', key + ' - ' + this.sampleSearchForm.value[key] );

                    if( this.sampleSearchForm.value[key].length > 0 ){
                        parameters += '&' + key + '=' + this.sampleSearchForm.value[key];
                    }else if( typeof this.sampleSearchForm.value[key] === 'boolean' ){
                        parameters += '&' + key + '=' + this.utilService.isTrue( this.sampleSearchForm.value[key] );
                     }else if( typeof this.sampleSearchForm.value[key] === 'number' ){
                        parameters += '&' + key + '=' + this.sampleSearchForm.value[key];
/*
                    }else if( typeof this.sampleSearchForm.value[key] === 'string' ){
                        parameters += '&' + key + '=' + this.sampleSearchForm.value[key];
*/
                    }else{
                        // console.error( 'MHL Unidentified type: ', key + '=' + this.sampleSearchForm.value[key] );
                        // console.error( 'MHL Unidentified TYPE: ',  typeof  this.sampleSearchForm.value[key] );
                    }
                }
            );
        return parameters.substr( 1 );
    }

}
