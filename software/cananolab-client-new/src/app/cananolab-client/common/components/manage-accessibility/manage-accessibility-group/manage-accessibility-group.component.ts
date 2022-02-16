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
        }
    }

    onCancelClick(){
        this.showGroupAccessEdit[this.index] = false;
        this.showGroupAccessEdit[this.index + 1] = false;
    }
}
