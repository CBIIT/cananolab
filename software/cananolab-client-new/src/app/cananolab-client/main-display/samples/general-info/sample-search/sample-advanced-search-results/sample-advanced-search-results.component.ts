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
        this.searchResults0 = this.sampleAdvancedSearchService.getSearchResults();
        this.dataRows = this.searchResults0;
    }

}
