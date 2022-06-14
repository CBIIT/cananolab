// ---------------------------------------------------------------------------
// ------ These will serve as constants used for Hiding, Showing, enabling, --
// ------ and disabling any of these menu options, by other components. ------
// ------ These values can also be used for the text of the menu item. -------
// ---------------------------------------------------------------------------

import { EventEmitter, Injectable } from '@angular/core';
import { MainDisplayService } from '../main-display/main-display.service';
import { Router } from '@angular/router';

/**
 * These will serve as constants used for Hiding, Showing, enabling,
 * and disabling any of these menu options, by other components.
 * These values can also be used for the text of the menu item.
 */
export let TopMenuItems = {
    HOME: 'Home',
    WORKFLOW: 'Workflow',
    PROTOCOLS: 'Protocols',
    SAMPLES: 'Samples',
    ADVANCED_SAMPLE: 'Advanced Sample',
    PUBLICATIONS: 'Publications',
    SUBMIT_PUBLICATIONS: 'Create Publication',
    SEARCH_PUBLICATIONS: 'Search Publication',
    SEARCH_SAMPLES_BY_PUBLICATIONS: 'Search for Samples by Publication',
    GROUPS: 'Groups',
    CURATION: 'Curation',
    RESULTS: 'Results',
    MY_WORKSPACE: 'My Workspace',
    MY_FAVORITES: 'My Favorites',
    ADMIN: 'ADMIN',
    HELP: 'Help',
    GLOSSARY: 'Glossary',
    LOGOUT: 'Logout',
    LOGIN:'Login',
    TEST: 'Test'
};
export let TopMenuData = [
    {
        'name': 'HOME',
        'displayName': 'Home',
        'route': 'home'
    },
    {
        'name': 'WORKFLOW',
        'displayName': 'Workflow',
        'route': 'home/workflow'
    },
    {
        'name': 'PROTOCOLS',
        'displayName': 'Protocols',
        'route': 'home/protocols'
    },
    {
        'name': 'SAMPLES',
        'displayName': 'Samples',
        'route': 'home/samples'
    },
    {
        'name': 'ADVANCED_SAMPLE',
        'displayName': 'Advanced Sample Search',
        'route': 'home/advancedsample'

    },
    {
        'name': 'PUBLICATIONS',
        'displayName': 'Publications',
        'route': 'home/publications'
    },
    {
        'name': 'SUBMIT_PUBLICATIONS',
        'displayName': 'Create Publication',
        'route': 'home/publications/submitPublication'
    },
    {
        'name': 'SEARCH_PUBLICATIONS',
        'displayName': 'Search Publications',
        'route': 'home/publications/searchPublication'
    },
    {
        'name': 'SEARCH_SAMPLES_BY_PUBLICATION',
        'displayName': 'Search for Samples by Publication',
        'route': 'home/publications/sample-search-by-publication'
    },
    {
        'name': 'GROUPS',
        'displayName': 'Groups',
        'route': 'home/groups'
    },
    {
        'name': 'CURATION',
        'displayName': 'Curation',
        'route': 'home/curation'
    },
    {
        'name': 'RESULTS',
        'displayName': 'Results',
        'route': 'home/curation/results'
    },
    {
        'name': 'MY_WORKSPACE',
        'displayName': 'My Workspace',
        'route': 'home/myworkspace'
    },
    {
        'name': 'MY_FAVORITES',
        'displayName': 'My Favorites',
        'route': 'home/myfavorites'
    },
    {
        'name': 'ADMIN',
        'displayName': 'Admin',
        'route': 'home/admin'
    },
    {
        'name': 'HELP',
        'displayName': 'Help',
        'route': 'home/help',
        'externalUrl':'https://wiki.nci.nih.gov/display/caNanoLab/caNanoLab+User%27s+Guide'
    },
    {
        'name': 'GLOSSARY',
        'displayName': 'Glossary',
        'route': 'home/glossary',
        'externalUrl':'https://wiki.nci.nih.gov/display/caNanoLab/caNanoLab+Glossary'
    },
    {
        'name': 'LOGOUT',
        'displayName': 'Logout',
        'route': 'home/logout'
    },
    {
        'name': 'LOGIN',
        'displayName': 'Login',
        'route': 'home/login'
    },
    {
        'name': 'TEST',
        'displayName': 'Test',
        'route': 'home/test'
    }

];

@Injectable( {
    providedIn: 'root'
} )
export class TopMainMenuService{

    topMenuValuesArray = null;
    topMenuKeysArray = null;

    enableMenuArray = [];
    enableTopMenuArrayEmitter = new EventEmitter();
    visibleMenuArray = [];
    visibleMenuArrayEmitter = new EventEmitter();

    constructor( private router:Router,private mainDisplayService: MainDisplayService ){
        this.init();
    }

    init(){
        this.topMenuValuesArray = Object.values( TopMenuItems );
        this.topMenuKeysArray = Object.keys( TopMenuItems );

        // Initialize to all enabled and all visible.
        for( let i = 0; i < this.topMenuKeysArray.length; i++ ){
            this.enableMenuArray[i] = true;
            this.visibleMenuArray[i] = true;
        }

        // Init the top menu
        this.showOnlyMenuItems(
            [
                'HELP',
                'GLOSSARY',
                'PROTOCOLS',
                'SAMPLES',
                'PUBLICATIONS'
            ]
        );

        this.updateEnableMenu();
        this.updateVisibleMenu();
    }

    /**
     * We shouldn't need this.
     */
    clearEnableMenuArray(){
        this.enableMenuArray = [];
    }

    /**
     * This should be called anytime "this.enableMenuArray" might have changed.
     * Tells subscribers that this is the new "enableMenuArray".
     *
     * The enableMenuArray determines if a particular menu item is disabled or enabled.
     */
    updateEnableMenu(){
        this.enableTopMenuArrayEmitter.emit( this.enableMenuArray );
    }

    /**
     * To get enableMenuArray directly rather than subscribe.
     */
    getEnableMenuArray(){
        return this.enableMenuArray;
    }


    /**
     * Enable an option in the Main Top Menu
     *
     * @param menuItem The "key" of the Main Top Menu option to enable
     */
    enableMenuItem( menuItem ){
        this.enableMenuArray[this.getIndexByKey( menuItem )] = true;
        this.updateEnableMenu();
    }

    /**
     * Disable an option in the Main Top Menu
     * It can still be visible in the menu, just disabled
     *
     * @param menuItem The "key" of the Main Top Menu option to disable
     */
    disableMenuItem( menuItem ){
        this.enableMenuArray[this.getIndexByKey( menuItem )] = false;
        this.updateEnableMenu();
    }








    /**
     * Make an item visible in the Main Top Menu
     *
     * @param menuItem The "key" of the Main Menu item to show in the menu
     */
    showMenuItem( menuItem ){
        this.visibleMenuArray[this.getIndexByKey( menuItem )] = true;
        this.updateVisibleMenu();
    }

    /**
     * A list of Items (by "key") to show in the MainTopMenu
     * All other options will not appear in the menu
     *
     * @param menuItems
     */
    showOnlyMenuItems( menuItems ){
        console.log(menuItems)

        this.hideAllMenuItems();
        for( let i = 0; i < menuItems.length; i++ ){
            this.visibleMenuArray[this.getIndexByKey( menuItems[i] )] = true;
        }
        this.updateVisibleMenu();
    }

    /**
     *
     * @param menuItem
     */
    showOnlyMenuItem( menuItem ){
        let temp = [menuItem];
        this.showOnlyMenuItems( temp );
    }


    /**
     * Remove an item from the Main Top Menu
     *
     * @param menuItem The "key" of the Main Menu item to be removed from the menu
     */
    hideMenuItem( menuItem ){
        this.visibleMenuArray[this.getIndexByKey( menuItem )] = false;
        this.updateVisibleMenu();
    }

    /**
     * Set all the Main Top Menu enabled and NOT visible
     */
    hideAllMenuItems(){
        for( let i = 0; i < this.topMenuKeysArray.length; i++ ){
            this.enableMenuArray[i] = true;
            this.visibleMenuArray[i] = false;
        }
        this.updateVisibleMenu();
    }

    updateVisibleMenu(){
        this.visibleMenuArrayEmitter.emit( this.visibleMenuArray );
    }

    getVisibleMenuArray(){
        return this.visibleMenuArray;
    }



    /**
     * Passes the new Top Main Menu selection on the Main Display
     *
     * @param ms Top Main Menu selection
     */
    selectMenuItem( ms ){
        this.mainDisplayService.setMenuSelection( ms );
    }


    /**
     * Returns the index of Main Top Menu option by its "key"
     *
     * @param key
     */
    getIndexByKey( key ){
        let n = -1;
        for( n = 0; n < this.topMenuKeysArray.length; n++ ){
            if( key.localeCompare( this.topMenuKeysArray[n] ) === 0 ){
                return n;
            }
        }
        return n;
    }
}
