import { Component, Input, OnInit } from '@angular/core';
import * as clone from 'clone';

@Component( {
    selector: 'canano-manage-accessibility',
    templateUrl: './manage-accessibility.component.html',
    styleUrls: ['./manage-accessibility.component.scss']
} )
export class ManageAccessibilityComponent implements OnInit{

    @Input() accessData = 'Place Holder_03';
    temp = 'Place Holder_01';

    constructor(){

    }

    ngOnInit(): void{
        if( this.accessData !== undefined ){
            this.temp = clone( this.accessData );
        }
    }

    but(){
    }
}
