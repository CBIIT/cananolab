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
    errors;
    constructor(private apiService: ApiService){
    }

    ngOnInit(): void{
        this.errors={};
    }

    onSearchSampByPubClick(){
        this.apiService.doGet( Consts.HELP_URL_SAMPLE_SEARCH_BY_PUBLICATIONS, 'id=' + this.inputId + '&type=' + this.type).subscribe(
            data => {
                console.log('onSearchSampByPubClick results: ', data);
            },
            err => {
                this.errors=err;
                console.error('ERROR onSearchSampByPubClick: ', err);
            }
            );
    }

    reset() {
        this.type='PubMed';
        this.inputId='';
    }
}
