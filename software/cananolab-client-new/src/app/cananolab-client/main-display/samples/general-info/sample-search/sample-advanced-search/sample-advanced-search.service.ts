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
    }

    getSearchResults(){
        return this.searchResults;
    }
}
