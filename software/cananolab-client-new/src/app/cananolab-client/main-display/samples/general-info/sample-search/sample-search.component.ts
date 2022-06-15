import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Consts } from '../../../../../constants';
import { ApiService } from '../../../../common/services/api.service';
import { UtilService } from '../../../../common/services/util.service';
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
    data;
    dataTrailer;
    errors;
    sampleSetupData={};
    loading;
    loadingMessage=Consts.searchingMessage;
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
        this.errors={};
        this.data={
            "text":"",
            "sampleName":"",
            "samplePointOfContact":"",
            "pocOperand":"contains",
            "nameOperand":"contains",
            "nanomaterialEntityTypes":[],
            "functionalizingEntityTypes":[],
            "functionTypes":[],
            "characterizationType":"",
            "characterizations":[]
        };
        this.dataTrailer=JSON.parse(JSON.stringify(this.data));
        this.init();
    }

    init(){
        this.loading=true;
        this.loadingMessage=Consts.loadingMessage;
        this.apiService.doGet( Consts.QUERY_SAMPLE_SETUP, '' ).subscribe(
            data => {
                this.loading=null;
                this.sampleSetupData = data;
            },
            error=> {
                this.loading=null;
            } );
    }

    onCharacterizationType(charType){
        this.characterizationType = charType;
        // QUERY_GET_CHARACTERIZATION_BY_TYPE
        this.apiService.doGet( Consts.QUERY_GET_CHARACTERIZATION_BY_TYPE, 'type=' + charType ).subscribe(
            data => {
                this.characterizationsList = <any>data;
            } );

    }

    onSubmit(){
        this.loading=true;
        this.loadingMessage=Consts.searchingMessage;
        // QUERY_SEARCH_SAMPLE
        this.apiService.doPost( Consts.QUERY_SEARCH_SAMPLE, this.data ).subscribe(
            data => {
                this.searchResults = <any>data;
                this.loading=null;
                // send search results to samplesSearchResults
                this.sampleSearchResultsService.setSearchResults( this.searchResults );
                this.router.navigate(['home/samples/sample-search-results']); // @FIXME TESTING  Don't hard code this!!!
            },
            error=> {
                this.loading=null;
                this.errors=error;
            } );
    }

    reset() {
        this.data=JSON.parse(JSON.stringify(this.dataTrailer));
    }

}
