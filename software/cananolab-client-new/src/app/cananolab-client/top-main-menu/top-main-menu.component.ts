// ----------------------------------------------------------------------------------------
// ---------------------              "Top Main Menu"              ------------------------
// ---------------------     The Top menu just below the header    ------------------------
// ----------------------------------------------------------------------------------------

import { Component, OnDestroy, OnInit } from '@angular/core';
import { TopMainMenuService, TopMenuData, TopMenuItems } from './top-main-menu.service';
import { UtilService } from '../common/services/util.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { ConfigurationService } from '../common/services/configuration.service';
import { Router,Event, NavigationStart, NavigationEnd, NavigationError } from '@angular/router';
import { Consts } from '../../constants';
import { Properties } from '../../../assets/properties';

@Component({
  selector: 'canano-top-main-menu',
  templateUrl: './top-main-menu.component.html',
  styleUrls: ['./top-main-menu.component.scss']
})
export class TopMainMenuComponent implements OnInit, OnDestroy {

    // For HTML access
    topMenuItems =  TopMenuItems;
    topMenuItemNames = TopMenuData;
    enableMenuArray = [];
    visibleMenuArray = [];
    properties = Properties;
    // @CHECKME  Should this be in the Properties file?
    defaultComponent = TopMenuData[0].route; // Just use the first one, it will be "home"
    currentRoute: string;
    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

  constructor( private topMainMenuService: TopMainMenuService, private utilService: UtilService,
               private router: Router) {
                this.router.events.subscribe((event: Event) => {
                    if (event instanceof NavigationStart) {
                        this.currentRoute=event.url;
                    }
                })
               }

 async ngOnInit() {
     this.topMainMenuService.enableTopMenuArrayEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
         ( data ) => {
             this.enableMenuArray = data;
         } );

     this.topMainMenuService.visibleMenuArrayEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
         ( data ) => {
             this.visibleMenuArray = data;
         } );

    this.enableMenuArray = this.topMainMenuService.getEnableMenuArray();
    this.visibleMenuArray = this.topMainMenuService.getVisibleMenuArray();
 }

    /**
     * Called when a Top Main Menu item is selected.
     * It is also call at ngOnInit to prime the default.
     *
     * @param item The "Value" (not the key) of the Main Menu item selected.
     */
    onMenuSelect(item){
        if( item['route'].includes( 'home/' + Consts.QUERY_LOGOUT )){
            this.topMainMenuService.hideMenuItem( 'LOGOUT' );
            Properties.LOGGED_IN = false;
            // Init the top menu
            this.topMainMenuService.showOnlyMenuItems(
                [
                    'HOME',
                    'HELP',
                    'GLOSSARY',
                    'PROTOCOLS',
                    'SAMPLES',
                    'PUBLICATIONS',
                    'LOGIN'
                ]
            ); }
            let keys=Object.keys(item);
            if (keys.indexOf('externalUrl')>-1) {
                window.open(item['externalUrl'])
            }
            else {
                this.router.navigate([item.route]);
            }

    }

    // Avoid memory leak
    ngOnDestroy(): void {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

}
