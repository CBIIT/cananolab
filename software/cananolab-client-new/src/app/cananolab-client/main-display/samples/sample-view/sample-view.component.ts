import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../constants';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Properties } from '../../../../../assets/properties';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { NavigationService } from 'src/app/cananolab-client/common/services/navigation.service';
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

    constructor( private navigationService:NavigationService,private route: ActivatedRoute, private httpClient: HttpClient,
                 private router: Router){
    }

    ngOnInit(): void{
        this.navigationService.setCurrentSelectedItem(0);
        this.route.params.subscribe(
            ( params: Params ) => {
                console.log( 'MHL 70 params: ', params );
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
                        console.log( 'MHL data.pointOfContactMap: ', data['pointOfContactMap'] );
                        console.log( 'MHL data.pointOfContactMap contactPerson: ', data['pointOfContactMap']['contactPerson'][0] );
                        Properties.CURRENT_SAMPLE_NAME = data['sampleName'];
                        this.pointOfContacts = this.sampleData['pointOfContactMap'];
                        console.log( 'MHL pointOfContacts: ', this.pointOfContacts );
                        this.keyWords = this.sampleData.keywords.split( /<br>/ );
                        this.toolHeadingNameViewSample = 'Sample ' + Properties.CURRENT_SAMPLE_NAME;
                    } );
            } );

    }

    topButtonGeneralInfo(){
        this.router.navigate( ['home/samples/view-sample', '?sampleId=' + Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
    }
    topButtonComposition(){
    }

    getSampleViewData(){
        let getUrl = Properties.API_SERVER_URL + '/caNanoLab/rest/sample/view?sampleId=' + this.sampleId;

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
            results = this.httpClient.get( getUrl, options ).pipe( timeout( Properties.HTTP_TIMEOUT ) );
        }catch( e ){
            // TODO react to error.
            console.error( 'doGet Exception: ' + e );
        }
        return results;
    }
}
