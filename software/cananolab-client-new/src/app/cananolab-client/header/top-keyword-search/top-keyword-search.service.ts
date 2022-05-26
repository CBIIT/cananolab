// -----------------------------------------------------------------------------------------
// ----------------    Service fot the Search at top right (just stubs)   ------------------
// -----------------------------------------------------------------------------------------

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TopKeywordSearchService {
    keywordResults;
    constructor() { }

    getKeywordResults() {
        let results = JSON.parse(localStorage.getItem('cananolab_search_results'));
        if (results) {
            return results
        }
        return results;
    }

    setKeywordResults(data) {
        this.keywordResults=data;
        localStorage.setItem('cananolab_search_results',JSON.stringify(data));
    }
}
