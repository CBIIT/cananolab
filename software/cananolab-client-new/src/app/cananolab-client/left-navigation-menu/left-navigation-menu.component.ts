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
@Component( {
    selector: 'canano-left-navigation-menu',
    templateUrl: './left-navigation-menu.component.html',
    styleUrls: ['./left-navigation-menu.component.scss']
} )
export class LeftNavigationMenuComponent implements OnInit{
    topHeading = 'Navigation Tree';
    currentSelectedItem = 0;
    sampleId;

    constructor( private navigationService: NavigationService,private route:ActivatedRoute,private router: Router, private utilService: UtilService ){
    }

    ngOnInit(): void{

        this.currentSelectedItem=this.navigationService.getCurrentSelectedItem();
        console.log(this.currentSelectedItem)
    }

    onCharacterizationClick(){
        this.currentSelectedItem = 2;
        this.router.navigate( ['home/samples/characterization', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
    }


    /**
     * @CHECKME Should this be view or edit?
     */
    onGeneralInfoClick(){
        this.currentSelectedItem = 0;
        // this.router.navigate( ['home/samples/samplesEdit', '?sampleId=' + Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        this.router.navigate( ['home/samples/samplesView',Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
    }

    onCompositionClick(){
        this.currentSelectedItem = 1;
        this.router.navigate( ['home/samples/composition',Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
    }

    onPublicationsClick(){
        this.currentSelectedItem = 3;
        this.router.navigate( ['home/samples/publications', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
    }

}
