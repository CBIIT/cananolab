import {  EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PointOfContactService {
    showPointOfContactEditorEmitter = new EventEmitter();
    hidePointOfContactEditorEmitter = new EventEmitter();
    showPointOfContactCreateEmitter = new EventEmitter();
    hidePointOfContactCreateEmitter = new EventEmitter();
    emitNewPocEmitter = new EventEmitter();

    // A new sample doesn't know its own Id until its POC is created
    sampleId = '';
    sampleIdEmitter = new EventEmitter();

  constructor() { }

    showPocEditor(){
      this.showPointOfContactEditorEmitter.emit( true );

    }
    hidePocEditor(){
      this.hidePointOfContactEditorEmitter.emit( false );
    }

    showPocCreate(){
      this.showPointOfContactCreateEmitter.emit( true );

    }
    hidePocCreate(){
      this.hidePointOfContactCreateEmitter.emit( false );
    }

    // For Create sample
    emitNewPoc( poc ){
        this.emitNewPocEmitter.emit( poc );
    }

/*
    setSampleId( sampleId ){
      this.sampleId = sampleId;
      this.sampleIdEmitter.emit( sampleId );
    }

    getSampleId(){
      return this.sampleId;
    }
    */
    // @TODO we will do this differently
    buildSavePocQuery( sampleName, pocArray ){
        let query = '{"sampleName": "' + sampleName + '",';
        query += '"sampleId": ' + pocArray[0]['sampleId'] + ',';
        query += '"pointOfContacts":[ ';
        for( let f = 0; f < pocArray.length; f++ ){
            query += '{';
            query += '"dirty": true,';
            query += '"id": ' + pocArray[f]['id'] + ',';
            query += '"contactPerson": "' + pocArray[f]['contactPerson'] + '",';
            query += '"sampleId": "' + pocArray[f]['sampleId'] + '",';

            query += '"organization":{ ';
            query += '"id": "' + pocArray[f]['organization']['id'] + '",';
            query += '"name": "' + pocArray[f]['organization']['name'] + '",';

            query += '"address": {';
            query += '"line1": "' + pocArray[f]['organization']['address']['line1'] + '",';
            query += '"line2": "' + pocArray[f]['organization']['address']['line2'] + '",';
            query += '"city": "' + pocArray[f]['organization']['address']['city'] + '",';
            query += '"stateProvince": "' + pocArray[f]['organization']['address']['stateProvince'] + '",';
            query += '"zip": "' + pocArray[f]['organization']['address']['zip'] + '",';
            query += '"country": "' + pocArray[f]['organization']['address']['country'] + '"';
            query += '}'; // END of ***organization*** address
            query += '},'; // END of organization

            query += '"role": "' + pocArray[0]['role'] + '",';
            // POC address
            query += '"address": {';
            query += '"line1": "' + pocArray[f]['address']['line1'] + '",';
            query += '"line2": "' + pocArray[f]['address']['line2'] + '",';
            query += '"city": "' + pocArray[f]['address']['city'] + '",';
            query += '"stateProvince": "' + pocArray[f]['address']['stateProvince'] + '",';
            query += '"zip": "' + pocArray[f]['address']['zip'] + '",';
            query += '"country": "' + pocArray[f]['address']['country'] + '"';
            query += '},'; // END of ***POC*** address

            query += '"firstName": "' + pocArray[f]['firstName'] + '",';
            query += '"lastName": "' + pocArray[f]['lastName'] + '",';
            query += '"middleInitial": "' + pocArray[f]['middleInitial'] + '",';
            query += '"phoneNumber": "' + pocArray[f]['phoneNumber'] + '",';
            query += '"email": "' + pocArray[f]['email'] + '",';
// @FIXME query += '"dirty": "' + pocArray[f]['dirty'] + '",';
            query += '"primaryContact": "' + pocArray[f]['primaryContact'] + '"';

            if( f < (pocArray.length - 1) ){
                query += ',';
            }


        } // END of poc loop

        query += '}]}';
        return query;
    }

}
