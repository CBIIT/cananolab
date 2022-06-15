import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../common/services/api.service';
import { Consts } from '../../../../constants';
import { MainDisplayService } from '../../main-display.service';
import { UtilService } from '../../../common/services/util.service';
import { Router } from '@angular/router';
import { SampleSearchResultsService } from '../../samples/general-info/sample-search/sample-search-results/sample-search-results.service';
import { SearchPublicationService } from '../../publications/search-publication/search-publication.service';
import { ProtocolsService } from '../../protocols/protocols.service';
@Component( {
    selector: 'canano-browse-cananolab',
    templateUrl: './browse-cananolab.component.html',
    styleUrls: ['./browse-cananolab.component.scss']
} )
export class BrowseCananolabComponent implements OnInit{
    initData = {};
    searchResults;

    constructor( private protocolsService:ProtocolsService,private searchPublicationService:SearchPublicationService,private sampleSearchResultsService: SampleSearchResultsService,private apiService: ApiService, private mainDisplayService: MainDisplayService,
                 private router: Router, private utilService: UtilService ){
    }

    ngOnInit(): void{
        this.init();

    }

    async init(){

        this.apiService.doGet( Consts.QUERY_INIT_SETUP, '' ).subscribe(
            data => {
                this.initData = data;
            } );

    }

    onSearchProtocolsClick(){
        this.router.navigate( [this.utilService.getRouteByName( 'PROTOCOLS' )] );
    }

    // Will not need this after router is in place?
    onSearchPublicationsClick(){
        this.router.navigate( [this.utilService.getRouteByName( 'PUBLICATIONS' )] );

    }

    onSearchSamplesClick(){
        this.router.navigate( [this.utilService.getRouteByName( 'SAMPLES' )] );

    }
    onSearchAllPublicationsClick() {
        let url = this.apiService.doPost(Consts.QUERY_PUBLICATION_SEARCH,
            {
                "category": null,
                "titleOperand": "contains",
                "nameOperand": "contains",
                "researchArea": [],
                "functionTypes": []
            });
        url.subscribe(data=> {
            this.searchPublicationService.setPublicationSearchResults(data);
            this.router.navigate(['home/publications/publication-search-results']);
        })


    }
    onSearchAllProtocolsClick() {
        console.log('test')
        let url = this.apiService.doPost(Consts.QUERY_SEARCH_PROTOCOL,{
                "nameOperand": "contains",
                "titleOperand": "contains",
                "abbreviationOperand": "contains",
                "fileTitle": "",
                "protocolType": "",
                "protocolName": ""
        });
        url.subscribe(data=>{
            this.protocolsService.setProtocolSearchResults(data);
            this.router.navigate(['home/protocols/protocol-search-results']);
        },
        errors=> {

        })

    }
    // searches and returns all samples publicly available to user //
    // redirects to sample results //
    onSearchAllSamplesClick() {
        let url = this.apiService.doPost(Consts.QUERY_SEARCH_SAMPLE,{});
        url.subscribe(
            data=> {
                this.searchResults = <any>data;

                // send search results to samplesSearchResults
                this.sampleSearchResultsService.setSearchResults( this.searchResults );
                this.router.navigate(['home/samples/sample-search-results']); // @FIXME TESTING  Don't hard code this!!!
        },
        error=> {
            console.log('error')
        });

    }
}
