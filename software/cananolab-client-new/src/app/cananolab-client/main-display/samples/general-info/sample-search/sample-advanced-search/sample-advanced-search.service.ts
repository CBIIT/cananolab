import { Injectable } from '@angular/core';

@Injectable( {
    providedIn: 'root'
} )
export class SampleAdvancedSearchService{
    searchResults = [];
    constructor(){
    }

    setSearchResults( sr ){
        this.searchResults = sr;
        console.log('MHL setSearchResults: ', this.searchResults );
    }

    getSearchResults(){
        console.log('MHL getSearchResults: ', this.searchResults );
        return this.searchResults;
    }
}
