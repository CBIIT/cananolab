import {  EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PointOfContactService {
    showPointOfContactEditorEmitter = new EventEmitter();
    hidePointOfContactEditorEmitter = new EventEmitter();
    showPointOfContactCreateEmitter = new EventEmitter();
    hidePointOfContactCreateEmitter = new EventEmitter();

  constructor() { }

    showPocEditor(){
      this.showPointOfContactEditorEmitter.emit( true );

    }
    hidePocEditor(){
      this.hidePointOfContactEditorEmitter.emit( false );
    }

    showPocCreate(){
      console.log('MHL showPointOfContactCreateEmitter.emit( true )');
      this.showPointOfContactCreateEmitter.emit( true );

    }
    hidePocCreate(){
      this.hidePointOfContactCreateEmitter.emit( false );
    }
}
