// ----------------------------------------------------------------------------------------
// -------------------            "Main Display Heading"          -------------------------
// ----------   The blueish bar across the top of the Main Display ------------------------
// ----------   Contains topic name, context sensitive Help link, and Glossary link  ------
// ----------------------------------------------------------------------------------------

import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { TopMenuItems } from '../../top-main-menu/top-main-menu.service';
import { takeUntil } from 'rxjs/operators';
import { MainDisplayService } from '../main-display.service';
import { Subject } from 'rxjs';

@Component( {
    selector: 'canano-main-display-heading',
    templateUrl: './main-display-heading.component.html',
    styleUrls: ['./main-display-heading.component.scss']
} )


export class MainDisplayHeadingComponent implements OnInit, OnDestroy{
    @Input() helpUrl = '';
    @Input() toolHeadingName = '';

    // For HTML access
    topMenuItems =  TopMenuItems;

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor(private mainDisplayService: MainDisplayService) {
    }

    ngOnInit(): void {
        this.mainDisplayService.menuSelectionEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            ( data ) => {
                this.toolHeadingName = data;
                this.setHelpUrl( );
            }
        );
    }

    // @FIXME This function should be moved to the Util service - it is duplicated elsewhere
    openHelpWindow( pageURL ) {
        window.open( pageURL, 'Help', 'alwaysRaised,dependent,status,scrollbars,resizable,width=800,height=500' );
    }

    /**
     * Sets the context sensitive Help link to the correct url for this tool (Top Menu selection)
     */
    setHelpUrl( ){
        switch( this.toolHeadingName ){

            case TopMenuItems.HOME:
                this.helpUrl = 'https://wiki.nci.nih.gov/display/caNanoLab/Getting+Started+in+caNanoLab#GettingStartedincaNanoLab-UsingtheWorkflowtoGetStarted';
                break;

            case TopMenuItems.WORKFLOW:
                this.helpUrl = 'https://wiki.nci.nih.gov/display/caNanoLab/Getting+Started+in+caNanoLab#GettingStartedincaNanoLab-UsingtheWorkflowtoGetStarted';
                break;

            case TopMenuItems.PROTOCOLS:
                this.helpUrl = 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Protocols+in+caNanoLab';
                break;

           case TopMenuItems.SAMPLES:
                this.helpUrl = 'https://wiki.nci.nih.gov/display/caNanoLab/Managing+Samples+in+caNanoLab#ManagingSamplesincaNanoLab-ManageSamples';
                break;

           case TopMenuItems.PUBLICATIONS:
                this.helpUrl = 'https://wiki.nci.nih.gov/x/e4QfEQ';
                break;

           case TopMenuItems.GROUPS:
                this.helpUrl = 'https://wiki.nci.nih.gov/x/fIQfEQ';
                break;

          case TopMenuItems.CURATION:
                this.helpUrl = 'https://wiki.nci.nih.gov/x/uoGAEQ';
                break;

          case TopMenuItems.MY_WORKSPACE:
                this.helpUrl = 'https://wiki.nci.nih.gov/x/fYQfEQ';
                break;

          case TopMenuItems.MY_FAVORITES:
                this.helpUrl = 'https://wiki.nci.nih.gov/x/b4AnEQ';
                break;
        }
    }

    // Avoid memory leaks
    ngOnDestroy(): void {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }


}
