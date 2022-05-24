import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SearchPublicationService {
    publicationSearchResults;
    constructor() { }

    // gets publication search results //
    getPublicationSearchResults() {
        let results = JSON.parse(localStorage.getItem('publicationSearchResults'));
        if (results) {
            return results
        }
        return this.publicationSearchResults;
    }

    // sets publicastion search results //
    setPublicationSearchResults(data) {
        this.publicationSearchResults=data;
        localStorage.setItem('publicationSearchResults',JSON.stringify(data));
    }



}
