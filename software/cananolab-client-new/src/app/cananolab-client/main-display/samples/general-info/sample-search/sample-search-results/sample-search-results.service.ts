import { EventEmitter, Injectable } from '@angular/core';


@Injectable( {
    providedIn: 'root'
} )
export class SampleSearchResultsService{

    searchResults ;
    searchResultsEmitter = new EventEmitter();

    constructor(){
    }

    setSearchResults( sr ){
        localStorage.setItem('cananolab_sample_search_results',JSON.stringify(sr));
        this.searchResults = sr;
        this.searchResultsEmitter.emit( sr );
    }

    getSearchResults(){
        let results = JSON.parse(localStorage.getItem('cananolab_sample_search_results'));
        if (results) {
            return results
        }
        return this.searchResults;
    }
}
