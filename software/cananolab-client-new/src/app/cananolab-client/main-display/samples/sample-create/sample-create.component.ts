import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../constants';
import { PointOfContactService } from '../../../point-of-contact/point-of-contact.service';
import { Properties } from '../../../../../assets/properties';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';

@Component( {
    selector: 'canano-sample-create',
    templateUrl: './sample-create.component.html',
    styleUrls: ['./sample-create.component.scss']
} )
export class SampleCreateComponent implements OnInit{
    helpUrl = Consts.HELP_URL_SAMPLE_EDIT;
    toolHeadingNameSearchSample = 'Create Sample';

    pointOfContacts = undefined;
    sampleData = undefined;
    showPointOfContactCreate = false;
    organizationNames = [];
    constructor(private pointOfContactService: PointOfContactService, private httpClient: HttpClient){
    }

    ngOnInit(): void{
        this.sampleData = this.getUserGroups().subscribe(  // @TODO move this to a common service
            data => {
                this.organizationNames = data;
               // this.pointOfContacts = this.sampleData.pointOfContacts; // FIXME we will not need this
            }
            );
    }



       // @TODO move this to a common service or component
        getUserGroups(){
        let getUrl = Properties.API_SERVER_URL + '/caNanoLab/rest/sample/submissionSetup';

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
        this.pointOfContactService.showPocCreate(  );
        this.showPointOfContactCreate = true;
    }


}
