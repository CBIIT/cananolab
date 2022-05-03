import { EventEmitter, Injectable } from '@angular/core';

@Injectable( {
    providedIn: 'root'
} )
export class NanomaterialService{
    nanomaterialSampleId;
    nanomaterialDataId;
    nanomaterialSetupData;
    nanomaterialEditData;

    newComposingElementShow = false;
    NewComposingElementShowEmitter =  new EventEmitter();

    constructor(){
    }

    setNewComposingElementShow(){
        this.newComposingElementShow = true;
        this.NewComposingElementShowEmitter.emit(true);
    }

   setNewComposingElementHide(){
       this.newComposingElementShow = false;
       this.NewComposingElementShowEmitter.emit(false);
   }

    getNanomaterialEditData(){
        return this.nanomaterialEditData;
    }

    setNanomaterialEditData( nmsData ){
        console.log('MHL setNanomaterialEditData: ', nmsData);
        this.nanomaterialEditData = nmsData;
    }

    getNanomaterialSetupData(){
        return this.nanomaterialSetupData;
    }

    setNanomaterialSetupData( nmsData ){
        console.log('MHL setNanomaterialSetupData: ', nmsData);
        this.nanomaterialSetupData = nmsData;
    }

    getNomaterialSampleId(){
        return this.nanomaterialSampleId;
    }

    setNomaterialSampleId( nmsId ){
        this.nanomaterialSampleId = nmsId;
    }

    getNomaterialDataId(){
        return this.nanomaterialDataId;
    }

    setNomaterialDataId( nmdId ){
        this.nanomaterialDataId = nmdId;
    }
}
