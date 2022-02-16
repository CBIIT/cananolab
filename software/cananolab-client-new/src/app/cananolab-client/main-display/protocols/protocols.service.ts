import { ProtocolScreen } from '../../../constants';
import { EventEmitter, Injectable } from '@angular/core';

@Injectable( {
    providedIn: 'root'
} )
export class ProtocolsService{
    currentProtocolInfo;
    currentProtocolScreen = ProtocolScreen.PROTOCOL_SEARCH_INPUT_SCREEN;
    currentProtocolScreenEmitter = new EventEmitter();

    constructor(){
    }

    setCurrentProtocolScreen( ps, info? ){
        this.currentProtocolScreen = ps;
        this.currentProtocolInfo = info;
        if( info !== undefined){
            this.currentProtocolScreenEmitter.emit( { ps, info } );
        }else{
          this.currentProtocolScreenEmitter.emit( { ps } );
        }
    }

    getCurrentProtocolScreen(){
        return this.currentProtocolScreen;
    }

    getCurrentProtocolInfo(){
        return this.currentProtocolInfo;
    }
}

//     PROTOCOL_SEARCH_RESULTS_SCREEN = 0;
//     PROTOCOL_SEARCH_INPUT_SCREEN = 1;
//     PROTOCOL_EDIT_SCREEN = 2;
