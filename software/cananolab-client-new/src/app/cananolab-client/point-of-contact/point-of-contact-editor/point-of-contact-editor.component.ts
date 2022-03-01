import { Component, Input, OnInit } from '@angular/core';
import { Consts } from '../../../constants';
import { UtilService } from '../../common/services/util.service';
import { ApiService } from '../../common/services/api.service';
import { PointOfContactService } from '../point-of-contact.service';

@Component( {
    selector: 'canano-point-of-contact-editor',
    templateUrl: './point-of-contact-editor.component.html',
    styleUrls: ['./point-of-contact-editor.component.scss']
} )
export class PointOfContactEditorComponent implements OnInit{
    @Input() poc = [];
    @Input() sampleData = [];

    constructor( private utilService: UtilService, private apiService: ApiService,
                 private pointOfContactService: PointOfContactService){
    }

    ngOnInit(): void{
        this.init();
    }

    async init(){
        while( this.poc === undefined ){
            await this.utilService.sleep( Consts.waitTime );
        }
        this.cleanOther();
    }


    onPocCancelClick(){
        this.pointOfContactService.hidePocEditor();
    }

    onPocSaveClick(){
        console.log('MHL onPocSaveClick');
        let q = this.buildQuery();
        this.apiService.doPost0( Consts.QUERY_SAMPLE_POC_UPDATE_SAVE, q).subscribe(
            (data) => {
                console.log('onPocSaveClick data: ', data );
                this.pointOfContactService.hidePocEditor();
            },
            (err) => {
                console.log('MHL onPocSaveClick err[\'error\']: ', err['error'][0]);
                alert('Error: ' + err['error'][0] );
            }
        );
    }

    buildQuery(){
        let query = '{"sampleName": "' + this.sampleData['sampleName'] + '",';
        query += '"sampleId": ' + this.poc[0]['sampleId'] + ',';
        query += '"pointOfContacts":[ ';
        for( let f = 0; f < this.poc.length; f++ ){
            query += '{';
            query += '"dirty": true,';
            query += '"id": ' + this.poc[f]['id'] + ',';
            query += '"contactPerson": "' + this.poc[f]['contactPerson'] + '",';
            query += '"sampleId": "' + this.poc[f]['sampleId'] + '",';

            query += '"organization":{ ';
            query += '"id": "' + this.poc[f]['organization']['id'] + '",';
            query += '"name": "' + this.poc[f]['organization']['name'] + '",';

            query += '"address": {';
            query += '"line1": "' + this.poc[f]['organization']['address']['line1'] + '",';
            query += '"line2": "' + this.poc[f]['organization']['address']['line2'] + '",';
            query += '"city": "' + this.poc[f]['organization']['address']['city'] + '",';
            query += '"stateProvince": "' + this.poc[f]['organization']['address']['stateProvince'] + '",';
            query += '"zip": "' + this.poc[f]['organization']['address']['zip'] + '",';
            query += '"country": "' + this.poc[f]['organization']['address']['country'] + '"';
            query += '}'; // END of ***organization*** address
            query += '},'; // END of organization

            query += '"role": "' + this.poc[0]['role'] + '",';
            // POC address
            query += '"address": {';
            query += '"line1": "' + this.poc[f]['address']['line1'] + '",';
            query += '"line2": "' + this.poc[f]['address']['line2'] + '",';
            query += '"city": "' + this.poc[f]['address']['city'] + '",';
            query += '"stateProvince": "' + this.poc[f]['address']['stateProvince'] + '",';
            query += '"zip": "' + this.poc[f]['address']['zip'] + '",';
            query += '"country": "' + this.poc[f]['address']['country'] + '"';
            query += '},'; // END of ***POC*** address

            query += '"firstName": "' + this.poc[f]['firstName'] + '",';
            query += '"lastName": "' + this.poc[f]['lastName'] + '",';
            query += '"middleInitial": "' + this.poc[f]['middleInitial'] + '",';
            query += '"phoneNumber": "' + this.poc[f]['phoneNumber'] + '",';
            query += '"email": "' + this.poc[f]['email'] + '",';
// @FIXME query += '"dirty": "' + this.poc[f]['dirty'] + '",';
            query += '"primaryContact": "' + this.poc[f]['primaryContact'] + '"';

            if( f < (this.poc.length - 1) ){
                query += ',';
            }


        } // END of poc loop

        query += '}]}';
        return query;
    }


    cleanOther(){
        let n = 0;
        while( n < this.sampleData['organizationNamesForUser'].length ){
            if( this.sampleData['organizationNamesForUser'][n] === '[other]' ){
                this.sampleData['organizationNamesForUser'].splice( n, 1 );
                n++;
            }
            n++;
        }
        this.sampleData['organizationNamesForUser'].push( '[other]' );


        while( n < this.sampleData['contactRoles'].length ){
            if( this.sampleData['contactRoles'][n] === '[other]' ){
                this.sampleData['contactRoles'].splice( n, 1 );
                n++;
            }
            n++;
        }
        this.sampleData['contactRoles'].push( '[other]' );
    }


}
