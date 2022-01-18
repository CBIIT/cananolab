import { Component, Input, OnInit } from '@angular/core';
import * as clone from 'clone';
import { UtilService } from '../../../services/util.service';

export const DATA_TYPE_ACCESS_MANAGE = { 'PROTOCOL': 'Protocol' };

@Component( {
    selector: 'canano-manage-accessibility-group',
    templateUrl: './manage-accessibility-group.component.html',
    styleUrls: ['./manage-accessibility-group.component.scss']
} )
export class ManageAccessibilityGroupComponent implements OnInit{
    @Input() accessData = 'Place Holder_02';
    @Input() dataType = '';
    @Input() showGroupAccessEdit;
    @Input() index = 0;

//     showGroupAccessEdit = {};

    accessType = 'NED';

    groupAccessData = 'Place Holder_01';

    constructor( private utilService: UtilService ){
    }

    ngOnInit(): void{
        this.init();
    }

    async init(){
        while( this.accessData === undefined ){
            await this.utilService.sleep( 50 );
        }
        if( this.accessData !== undefined ){
            this.groupAccessData = clone( this.accessData );
            this.accessType = this.groupAccessData['roleDisplayName'].trim();

            console.log( 'MHL clone groupAccessData: ', this.groupAccessData );
            console.log( 'MHL accessType: ', this.accessType );
        }

        if( this.accessData !== undefined && this.accessData['recipientDisplayName'] !== undefined ){
            // this.temp = clone( this.accessData) ;
            console.log( 'MHL ManageAccessibilityComponent data: ', this.accessData['recipientDisplayName'] );
            console.log( 'MHL ManageAccessibilityComponent temp: ', this.groupAccessData['recipientDisplayName'] );
        }
        // this.dataType = this.groupAccessData
console.log('MHL From parent showGroupAccessEdit: ', this.showGroupAccessEdit);
    }

    onCancelClick(){
       // this.showGroupAccessEdit =  { dog: 'BlackDog', val: true };
        console.log('MHL 100 Child showGroupAccessEdit: ', this.showGroupAccessEdit);
        console.log('MHL 101 Child showGroupAccessEdit: ', this.showGroupAccessEdit[this.index]);
        this.showGroupAccessEdit[this.index] = false;
        this.showGroupAccessEdit[this.index + 1] = false;
        console.log('MHL 102 Child showGroupAccessEdit: ', this.showGroupAccessEdit[this.index]);
    }
}
