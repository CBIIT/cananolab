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
    @Input() composingElementsArray;
    showAddNewComposingElement = false;
    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();
    constructor( private utilService: UtilService, private nanomaterialService: NanomaterialService ){
    }

    ngOnInit(): void{
        console.log( 'MHL 007 composingElementsArray: ', this.composingElementsArray );

        this.nanomaterialService.NewComposingElementShowEmitter.pipe( takeUntil( this.ngUnsubscribe ) )
            .subscribe( ( data ) => {
                if( ! data ){
                    this.showAddNewComposingElement = false;
                }
            } );

        this.init();
    }


    async init(){
        let runaway = 20;
        while( this.composingElementsArray === undefined && runaway > 0 ){
            await this.utilService.sleep( 125 )
            runaway--;
        }
        console.log( 'MHL 008 composingElementsArray: ', this.composingElementsArray );
    }

    showAddNewComposingElementForm(){
        this.showAddNewComposingElement = true;
        this.nanomaterialService.setNewComposingElementShow();
    }


}
