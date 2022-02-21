import { Component, Input, OnInit } from '@angular/core';
import { PointOfContactService } from '../point-of-contact.service';
import { UtilService } from '../../common/services/util.service';

@Component( {
    selector: 'canano-point-of-contact-create',
    templateUrl: './point-of-contact-create.component.html',
    styleUrls: ['./point-of-contact-create.component.scss']
} )
export class PointOfContactCreateComponent implements OnInit{
    @Input() sampleData = [];

    firstName = '';
    middleInitial = '';
    lastName = '';
    phoneNumber = '';
    email = '';
    addressLine1 = '';
    addressLine2 = '';
    addressCity = '';
    addressStateProvince = '';
    addressZip = '';
    addressCountry = '';


    showPocCreate = false;

    constructor( private pointOfContactService: PointOfContactService, private utilService: UtilService ){
    }

    ngOnInit(): void{
        this.pointOfContactService.showPointOfContactCreateEmitter.subscribe(
            ( data ) => {
                this.showPocCreate = data;
            }
        );

        if( this.sampleData === undefined){
            this.sampleData = [];
        }
    }

    onPocCreateSaveClick(){
        console.log( 'onPocCreateSaveClick' );
    }

    onPocCreateCancelClick(){
        console.log( 'onPocCreateCancelClick' );
        this.showPocCreate = false;
    }

}
