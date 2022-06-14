import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SearchSamplesByPublicationService {
    samplesByPublicationResults;
    constructor() { }

    // gets publication search results //
    getPublicationSearchResults() {
        let results = JSON.parse(localStorage.getItem('cananolab_samples_by_publication_results'));
        if (results) {
            return results
        }
        return this.samplesByPublicationResults;
    }

    // sets publicastion search results //
    setPublicationSearchResults(data) {
        this.samplesByPublicationResults=data;
        localStorage.setItem('cananolab_samples_by_publication_results',JSON.stringify(data));
    }
}
