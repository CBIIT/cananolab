import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { SampleAdvancedSearchService } from '../sample-advanced-search/sample-advanced-search.service';

@Component( {
    selector: 'canano-sample-advanced-search-results',
    templateUrl: './sample-advanced-search-results.component.html',
    styleUrls: ['./sample-advanced-search-results.component.scss']
} )
export class SampleAdvancedSearchResultsComponent implements OnInit{

    @Input() searchResults;
    myParam: string;
    searchResults0 = [];
    dataRows = [];
    constructor( private route: ActivatedRoute,
                 private sampleAdvancedSearchService: SampleAdvancedSearchService){
    }

    ngOnInit(): void{
/* Tryed to get the results by passing it in the router url, didn't work right @TODO It is worth revisiting.
        this.route.params.subscribe( ( params: Params ) => {
            console.log('MHL 000 params: ', params);
            this.myParam = params['queryObject'];
            console.log( 'MHL 001 myParam: ', this.myParam );
        } );*/

        this.searchResults0 = this.sampleAdvancedSearchService.getSearchResults();
        this.dataRows = this.searchResults0;
    }

}
