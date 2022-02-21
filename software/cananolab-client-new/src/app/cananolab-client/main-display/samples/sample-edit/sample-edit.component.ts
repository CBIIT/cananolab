import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../constants';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../assets/properties';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { PointOfContactService } from '../../../point-of-contact/point-of-contact.service';

@Component( {
    selector: 'canano-sample-edit',
    templateUrl: './sample-edit.component.html',
    styleUrls: ['./sample-edit.component.scss']
} )
export class SampleEditComponent implements OnInit{
    helpUrl = Consts.HELP_URL_SAMPLE_EDIT;
    toolHeadingNameSearchSample = 'Update Sample';
    sampleId = -1;
    sampleData;
    myParam;

    pointOfContacts;
    showPointOfContactCreate = false;

    constructor( private route: ActivatedRoute, private httpClient: HttpClient,
                 private pointOfContactService: PointOfContactService ){
    }

    ngOnInit(): void{
        this.route.params.subscribe(
            ( params: Params ) => {
                this.sampleId = params['sampleId'].replace( /^.*\?sampleId=/, '' );
                this.sampleData = this.getSampleEditData().subscribe(
                    data => {
                        this.sampleData = data;
                        this.pointOfContacts = this.sampleData.pointOfContacts;
                    } );
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

//    http://cent16:8090/caNanoLab/rest/sample/edit?sampleId=88932368
    // GET
    // 	http://cent16:8090/caNanoLab/rest/sample/edit?sampleId=88932368
}
