// -----------------------------------------------------------------------------------------
// ------                    @TODO This is no longer used                          ---------
// ----------------------           "Main Display" Service          ------------------------
// ---------   Used by the Top Manin menu, to tell the Main Display what to show    --------
// -----------------------------------------------------------------------------------------

import {  EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MainDisplayService {
    menuSelectionEmitter = new EventEmitter();

    constructor() {}

    /**
     * Called when a "Top Main Menu" choice is made.
     * subscribed to by main-display so it knows which top level component to show.
     *
     * @param ms The menu selection
     */
    setMenuSelection( ms ){
        this.menuSelectionEmitter.emit( ms );
    }
}
