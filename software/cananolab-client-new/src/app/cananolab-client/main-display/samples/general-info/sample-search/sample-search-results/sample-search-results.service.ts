import { EventEmitter, Injectable } from '@angular/core';


@Injectable( {
    providedIn: 'root'
} )
export class SampleSearchResultsService{

    searchResults;
    sampleIds=[];
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

    getSampleIds() {
        this.sampleIds=[];
        let results = JSON.parse(localStorage.getItem('cananolab_sample_search_results'));
        if (results) {
            // do samples ids stuff
        }
        else {
            results=this.searchResults;
        }
        results.forEach(sample=> {
            this.sampleIds.push(sample.sampleId)
        })
        console.log(this.sampleIds)
        return this.sampleIds;
    }
}
