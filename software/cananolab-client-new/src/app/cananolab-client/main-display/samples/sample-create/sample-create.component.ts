import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../constants';
import { PointOfContactService } from '../../../point-of-contact/point-of-contact.service';
import { Properties } from '../../../../../assets/properties';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { takeUntil, timeout } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component( {
    selector: 'canano-sample-create',
    templateUrl: './sample-create.component.html',
    styleUrls: ['./sample-create.component.scss']
} )
export class SampleCreateComponent implements OnInit{
    helpUrl = Consts.HELP_URL_SAMPLE_EDIT;
    toolHeadingNameSearchSample = 'Create Sample';

    pointOfContacts = [];
    userGroups = undefined;
    sampleData = {};
    sampleName = '';
    sampleId = '';
    showPointOfContactCreate = false;
    organizationNames = [];
    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor(private pointOfContactService: PointOfContactService, private httpClient: HttpClient){
    }

    ngOnInit(): void{
        this.userGroups = this.getUserGroups().subscribe(  // @TODO move this to a common service
            data => {
                this.organizationNames = data;
            }
            );

        this.pointOfContactService.emitNewPocEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            (data) => {
                console.log('MHL pointOfContactService.emitNewPocEmitter data: ', data);
                this.sampleId = data['sampleId'];
                this.pointOfContacts.push(data);
                console.log('MHL sampleId: ', this.sampleId);
        });

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

    onSaveSample(){
        /*
        {
   "sampleName":"vvvvv vvvvv",
   "newSampleName":null,
   "sampleId":0,
   "pointOfContacts":[
      {
         "organization":{
            "name":"BWH_AnesthesiologyD"
         },
         "role":"Program Manager",
         "dirty":true
      }
   ],
   "keywords":[

   ]
         */

        this.sampleData['sampleName'] = this.sampleName;
        this.sampleData['newSampleName'] = null;
        this.sampleData['sampleId'] = 0;
        console.log('MHL onSaveSample sampleName: ', this.sampleName);
        console.log('MHL this.pointOfContacts: ', this.pointOfContacts);

        this.pocForSampleSubmit();
    }

    pocForSampleSubmit(){
        let pocArray = [];
        for (let i = 0; i < this.pointOfContacts.length; i++) {
            console.log(this.pointOfContacts[i]);
            let temp = {};
            temp['organization'] = { }
        }
    }


    onAddPocClick(){
        this.pointOfContactService.showPocCreate(  );
        this.showPointOfContactCreate = true;
    }


}
