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
        console.log( 'MHL setSearchResults: ', sr );
        this.searchResults = sr;
        this.searchResultsEmitter.emit( sr );
    }

    getSearchResults(){
        return this.searchResults;
    }
}
