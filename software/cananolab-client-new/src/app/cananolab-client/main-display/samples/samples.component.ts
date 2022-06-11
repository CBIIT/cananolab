import { Component, OnInit, ViewChild } from '@angular/core';
import { Consts, SampleScreen } from '../../../constants';
import { ApiService } from '../../common/services/api.service';
import { NgForm } from '@angular/forms';
import { Properties } from '../../../../assets/properties';
import { UtilService } from '../../common/services/util.service';

@Component( {
    selector: 'canano-samples',
    templateUrl: './samples.component.html',
    styleUrls: ['./samples.component.scss']
} )
export class SamplesComponent implements OnInit{

// QUERY_SAMPLE_SETUP
    sampleScreen = SampleScreen;
    sampleScreenToShow = SampleScreen.SAMPLE_MANAGE_SCREEN; // Starting screen for Samples
    properties=Properties;
    toolHeadingNameManage = 'Manage Samples';
    helpUrl = Consts.HELP_URL_SAMPLE_SEARCH;
    toolHeadingNameSearchResults = 'Sample Search Results';
    toolHeadingNameSearch = 'Sample Search';

    constructor( private apiService: ApiService, private utilService: UtilService ){
    }

    ngOnInit(): void{
    }

    onCreateSampleClick(){
    }
}
