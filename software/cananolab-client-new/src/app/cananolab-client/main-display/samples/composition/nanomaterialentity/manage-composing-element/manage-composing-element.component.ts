import { Component, Input, OnInit } from '@angular/core';
import { UtilService } from '../../../../../common/services/util.service';
import { NanomaterialService } from '../nanomaterial.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component( {
    selector: 'canano-manage-composing-element',
    templateUrl: './manage-composing-element.component.html',
    styleUrls: ['./manage-composing-element.component.scss']
} )
export class ManageComposingElementComponent implements OnInit{
    @Input() composingElementsArray = [];
    showAddNewComposingElement = false;
    showEditComposingElement = false;
    elementTrailer = {};
    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private utilService: UtilService, private nanomaterialService: NanomaterialService ){
    }

    ngOnInit(): void{
        this.nanomaterialService.newComposingElementShowEmitter.pipe( takeUntil( this.ngUnsubscribe ) )
            .subscribe( ( data ) => {
                this.showAddNewComposingElement = data;
                if( ! data ){
                    this.showAddNewComposingElement = false;
                }
            } );

        // Sends the index
        this.nanomaterialService.editComposingElementShowEmitter.pipe( takeUntil( this.ngUnsubscribe ) )
            .subscribe( ( data ) => {
                this.showEditComposingElement = data;
                if( ! data ){
                    this.showEditComposingElement = false;
                }

            } );

        this.nanomaterialService.editComposingElementCancelEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe( (data) => {
            this.composingElementsArray[data] = {...this.elementTrailer};
        } );
        this.init();
    }


    async init(){
        let runaway = 20;
        while( this.composingElementsArray === undefined && runaway > 0 ){
            await this.utilService.sleep( 125 )
            runaway--;
        }
    }

    showAddNewComposingElementForm(){
        this.showAddNewComposingElement = true;
        this.nanomaterialService.setNewComposingElementShow();
    }

    editClick( compElement, i ){
        this.nanomaterialService.setEditComposingElementShow();
        this.nanomaterialService.editComposingElement( i );
        this.elementTrailer = {...this.composingElementsArray[i]};
    }

}
