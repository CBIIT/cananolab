// -----------------------------------------------------------------------------------------
// ------                    @TODO This is no longer used                          ---------
// ----------------------               "Main Display"              ------------------------
// --------- The large central display area below and to the right of menus, etc... --------
// -----------------------------------------------------------------------------------------

import { Component, OnDestroy, OnInit } from '@angular/core';
import { MainDisplayService } from './main-display.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { TopMenuItems } from '../top-main-menu/top-main-menu.service';

@Component( {
    selector: 'canano-main-display',
    template: '<html></html>',
    styleUrls: ['./main-display.component.scss']
} )
export class MainDisplayComponent implements OnInit, OnDestroy{
    currentMenuChoice = '';

    // For HTML access
    topMenuItems =  TopMenuItems;

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private mainDisplayService: MainDisplayService ) {
    }


    ngOnInit(): void {
        // Listen for Top Main Menu changes
        this.mainDisplayService.menuSelectionEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            ( data ) => {
                this.currentMenuChoice = data;
            } );
    }


    // Avoid memory leak
    ngOnDestroy(): void {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

}
