// -------------------------------------------------------------------------
// ---------------------  "Navigation Tree" top left  ----------------------
// ---------------  (General Info, Composition, Publication)  --------------
// ---------------------  @TODO This will be dynamic  ----------------------
// -------------------------------------------------------------------------

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from '../common/services/util.service';
import { Properties } from '../../../assets/properties';
import { ActivatedRoute } from '@angular/router';
import { NavigationService } from '../common/services/navigation.service';
import { Params } from '@angular/router';
import { StatusDisplayService } from '../status-display/status-display.service';
import { ThrowStmt } from '@angular/compiler';

@Component( {
    selector: 'canano-left-navigation-menu',
    templateUrl: './left-navigation-menu.component.html',
    styleUrls: ['./left-navigation-menu.component.scss']
} )
export class LeftNavigationMenuComponent implements OnInit{
    topHeading = 'Navigation Tree';
    currentSelectedItem = 0;
    sampleId;
    isEdit=false;
    constructor( private statusDisplayService:StatusDisplayService,private navigationService: NavigationService,private route:ActivatedRoute,private router: Router, private utilService: UtilService ){
    }

    ngOnInit(): void{
        if (this.statusDisplayService.isEditUrl()) {
            this.isEdit=true;
        }
        else {
            this.isEdit=false;
        }
        this.currentSelectedItem=this.navigationService.getCurrentSelectedItem();
        console.log(this.currentSelectedItem)
    }

    onCharacterizationClick(){
        this.currentSelectedItem = 2;
        console.log(this.isEdit)

        if (this.isEdit) {
            this.router.navigate( ['home/samples/characterization', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
        else {
            this.router.navigate( ['home/samples/view-characterization', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
    }


    /**
     * @CHECKME Should this be view or edit?
     */
    onGeneralInfoClick(){
        this.currentSelectedItem = 0;
        if (this.isEdit) {
            this.router.navigate( ['home/samples/sample',Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
        else {
            this.router.navigate( ['home/samples/view-sample',Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
    }

    onCompositionClick(){
        this.currentSelectedItem = 1;
        console.log(this.isEdit)
        if (this.isEdit) {
            this.router.navigate( ['home/samples/composition',Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
        else {
            this.router.navigate( ['home/samples/view-composition',Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
    }

    onPublicationsClick(){
        console.log(this.isEdit)

        this.currentSelectedItem = 3;
        if (this.isEdit) {
            this.router.navigate( ['home/samples/publications', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
        else {
            this.router.navigate( ['home/samples/view-publications', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
    }

}
