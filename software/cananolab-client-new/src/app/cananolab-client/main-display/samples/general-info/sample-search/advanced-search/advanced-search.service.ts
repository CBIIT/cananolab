import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdvancedSearchService {
    advancedSearchResults;
    constructor() { }

    getAdvancedSearchResults() {
        let results = JSON.parse(localStorage.getItem('cananolab_advanced_search_results'));
        if (results) {
            return results
        }
        else {
            return this.advancedSearchResults;
        }
    }

    setAdvancedSearchResults(data) {
        this.advancedSearchResults=data;
        localStorage.setItem('cananolab_advanced_search_results',JSON.stringify(data));
    }
}
