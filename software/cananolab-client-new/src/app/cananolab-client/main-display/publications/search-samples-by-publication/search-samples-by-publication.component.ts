import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../constants';
import { ApiService } from '../../../common/services/api.service';

@Component( {
    selector: 'canano-search-samples-by-publication',
    templateUrl: './search-samples-by-publication.component.html',
    styleUrls: ['./search-samples-by-publication.component.scss']
} )
export class SearchSamplesByPublicationComponent implements OnInit{
    helpUrl = Consts.HELP_URL_SAMPLE_SEARCH_BY_PUBLICATIONS;
    toolHeadingName = 'Sample Search by Publication';

    type = 'PubMed';
    inputId = '';
    constructor(private apiService: ApiService){
    }

    ngOnInit(): void{
    }

    onSearchSampByPubClick(){
// this.apiService
//        rest/publication/searchById?id=9&type=DOI
        // this.apiService.doGet( Consts.QUERY_SEARCH, 'keyword=' + this.topKeyWordSearchText ).subscribe(

        this.apiService.doGet( Consts.HELP_URL_SAMPLE_SEARCH_BY_PUBLICATIONS, 'id=' + this.inputId + 'type=' + this.type).subscribe(
            data => {
                console.log('MHL onSearchSampByPubClick results: ', data);
            },
            err => {
                console.error('MHL ERROR onSearchSampByPubClick: ', err);
            }
            );
    }
}
