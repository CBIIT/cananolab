import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../constants';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Properties } from '../../../../../assets/properties';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { NavigationService } from 'src/app/cananolab-client/common/services/navigation.service';
import { ApiService } from 'src/app/cananolab-client/common/services/api.service';
@Component( {
    selector: 'canano-sample-view',
    templateUrl: './sample-view.component.html',
    styleUrls: ['./sample-view.component.scss']
} )
export class SampleViewComponent implements OnInit{
    sampleId = -1;
    sampleName = '';
    sampleData;

    helpUrl = Consts.HELP_URL_SAMPLE_VIEW;
    toolHeadingNameViewSample = 'Sample ' + this.sampleName;

    pointOfContacts = [];
    keyWords = [];

    constructor(
        private navigationService:NavigationService,
        private route: ActivatedRoute,
        private httpClient: HttpClient,
        private router: Router,
        private apiService:ApiService){
    }

    ngOnInit(): void{
        this.navigationService.setCurrentSelectedItem(0);
        this.route.params.subscribe(
            ( params: Params ) => {
                this.sampleId = params['sampleId'].replace( /^.*\?sampleId=/, '' ).replace( /&.*$/, '' );
                this.sampleName = params['sampleId'].replace( /^.*sampleName=/, '' );
                this.toolHeadingNameViewSample = 'Sample ' + this.sampleName;
            } );


        this.route.params.subscribe(
            ( params: Params ) => {
                this.sampleId = params['sampleId'].replace( /^.*\?sampleId=/, '' );
                if(
                    this.sampleId <= 0 ){
                    this.sampleId = Properties.CURRENT_SAMPLE_ID;
                }else{
                    Properties.CURRENT_SAMPLE_ID = this.sampleId;
                }

                this.sampleData = this.getSampleViewData().subscribe(
                    data => {
                        Properties.SAMPLE_TOOLS = true;
                        this.sampleData = data;
                        Properties.CURRENT_SAMPLE_NAME = data['sampleName'];
                        this.pointOfContacts = this.sampleData['pointOfContactMap'];
                        this.keyWords = this.sampleData.keywords.split( /<br>/ );
                        this.toolHeadingNameViewSample = 'Sample ' + Properties.CURRENT_SAMPLE_NAME;
                    } );
            } );

    }

    topButtonGeneralInfo(){
        this.router.navigate( ['home/samples/view-sample', '?sampleId=' + Properties.CURRENT_SAMPLE_ID] );
    }
    topButtonComposition(){
        this.router.navigate( ['home/samples/composition', Properties.CURRENT_SAMPLE_ID] );
    }

    topButtonCharacterization(){
        this.router.navigate( ['home/samples/characterization', Properties.CURRENT_SAMPLE_ID] );
    }

    topButtonPublication(){
        this.router.navigate( ['home/samples/publications', Properties.CURRENT_SAMPLE_ID] );
    }

    getSampleViewData(){
        let getUrl = Consts.QUERY_SAMPLE_VIEW;

        if( Properties.DEBUG_CURL ){
            let curl = 'curl  -k \'' + getUrl + '\'';
            console.log( curl );
        }

        let headers = new HttpHeaders( {
            'Content-Type': 'application/x-www-form-urlencoded'
        } );

        let options = {
            headers: headers,
            method: 'get',
        };

        let results;
        try{
            results = this.apiService.doGet(getUrl,'sampleId='+this.sampleId);
        }catch( e ){
            // TODO react to error.
            console.error( 'doGet Exception: ' + e );
        }
        return results;
    }
}
