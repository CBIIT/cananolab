import { ProtocolScreen } from '../../../constants';
import { EventEmitter, Injectable } from '@angular/core';

@Injectable( {
    providedIn: 'root'
} )
export class ProtocolsService{
    currentProtocolInfo;
    currentProtocolScreen = ProtocolScreen.PROTOCOL_SEARCH_INPUT_SCREEN;
    protocolSearchResults;
    currentProtocolScreenEmitter = new EventEmitter();

    constructor(){
    }

    // gets publication search results //
    getProtocolSearchResults() {
        let results = JSON.parse(localStorage.getItem('cananolab_protocol_search_results'));
        if (results) {
            return results
        }
        return this.protocolSearchResults;
    }

    // sets publicastion search results //
    setProtocolSearchResults(data) {
        this.protocolSearchResults=data;
        localStorage.setItem('cananolab_protocol_search_results',JSON.stringify(data));
    }
    setCurrentProtocolScreen( ps, info? ){
        console.log('MHL 00  setCurrentProtocolScreen ps: ', ps);
        console.log('MHL 01  setCurrentProtocolScreen info: ', info);
        this.currentProtocolScreen = ps;
        this.currentProtocolInfo = info;
        if( info !== undefined){
            console.log('MHL 02  setCurrentProtocolScreen info: ', info);
            this.currentProtocolScreenEmitter.emit( { ps, info } );
        }else{
            console.log('MHL 03  setCurrentProtocolScreen ps: ', ps);
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
