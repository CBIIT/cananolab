import { Component, OnDestroy, OnInit } from '@angular/core';
import { Consts } from '../../../../../constants';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { PointOfContactService } from '../../../../point-of-contact/point-of-contact.service';
import { ApiService } from '../../../../common/services/api.service';
import { NavigationService } from '../../../../common/services/navigation.service';
import { SampleAvailabilityDisplayService } from '../sample-search/sample-search-results/sample-availability-display/sample-availability-display.service';
@Component( {
    selector: 'canano-sample-edit',
    templateUrl: './sample-edit.component.html',
    styleUrls: ['./sample-edit.component.scss']
} )
export class SampleEditComponent implements OnInit, OnDestroy{
    helpUrl = Consts.HELP_URL_SAMPLE_EDIT;
    toolHeadingNameSearchSample = 'Update Sample';
    sampleId = -1;
    sampleData;
    myParam;
    newKeyword = '';
    pointOfContacts;
    showPointOfContactCreate = false;

    constructor( private navigationService: NavigationService, private route: ActivatedRoute, private httpClient: HttpClient,
                 private pointOfContactService: PointOfContactService, private apiService: ApiService,
                 private sampleAvailabilityDisplayService: SampleAvailabilityDisplayService){
    }

    ngOnInit(): void{
        this.navigationService.setCurrentSelectedItem(0);
        this.route.params.subscribe(
            ( params: Params ) => {
                this.sampleId = params['sampleId'].replace( /^.*\?sampleId=/, '' );
                if(
                    this.sampleId <= 0 ){
                    this.sampleId = Properties.CURRENT_SAMPLE_ID;
                }else{
                    Properties.CURRENT_SAMPLE_ID = this.sampleId;
                }

                this.sampleData = this.getSampleEditData().subscribe(
                    data => {
                        Properties.SAMPLE_TOOLS = true;
                        this.sampleData = data;
                        Properties.CURRENT_SAMPLE_NAME = data['sampleName'];
                        this.pointOfContacts = this.sampleData.pointOfContacts;
                    } );
            } );
    }

    onAddKeyword( newKeyword ){
        this.sampleData['keywords'].push( newKeyword );
        this.newKeyword = '';
    }

    onSampleDeleteClick(){
        console.log( 'onSampleDeleteClick' );
    }

    onSampleCopyClick(){
        console.log( 'onSampleCopyClick' );
    }

    onSampleResetClick(){
        console.log( 'onSampleResetClick' );
    }

    onSampleUpdateClick(){
        console.log( 'onSampleUpdateClick sampleName: ', this.sampleData['sampleName'] );
        console.log( 'onSampleUpdateClick sampleId: ', this.sampleData['sampleId'] );
        console.log( 'onSampleUpdateClick keywords: ', this.sampleData['keywords'] );
        this.updateSample();
    }

    updateSample(){
        let su = {};
        su['sampleName'] = this.sampleData['sampleName'];
        su['sampleId'] = this.sampleData['sampleId'];
        su['keywords'] = this.sampleData['keywords'];

        this.apiService.doPost( Consts.QUERY_SAMPLE_UPDATE, su ).subscribe(
            data => {
                console.log( 'Return data from QUERY_SAMPLE_UPDATE: ', data );
            },
            ( err ) => {
                console.log( 'ERROR QUERY_SAMPLE_UPDATE: ', err );
            } );

    }

    getSampleEditData(){
        let getUrl = Properties.API_SERVER_URL + '/caNanoLab/rest/sample/edit?sampleId=' + this.sampleId;

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

    onAddPocClick(){
        this.pointOfContactService.showPointOfContactCreateEmitter.emit( true );
        this.showPointOfContactCreate = true;
    }


    onAvailabilityClick( id ){
        this.apiService.doGet( Consts.QUERY_SAMPLE_AVAILABILITY, 'sampleId=' + id).subscribe(
            data => {
                this.sampleAvailabilityDisplayService.displayStuff( data );
            },
            ( err ) => {
                console.log( 'ERROR QUERY_SAMPLE_AVAILABILITY: ', err );
            } );
    }

    ngOnDestroy(): void{
    }

}
