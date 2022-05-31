import { EventEmitter, Injectable } from '@angular/core';

@Injectable( {
    providedIn: 'root'
} )
export class NanomaterialService{
    nanomaterialSampleId;
    nanomaterialDescription;
    nanomaterialDataId;
    nanomaterialSetupData;
    nanomaterialEditData;
    composingElementsArray = [];
    newNanomaterialType;

    newComposingElementShowEmitter =  new EventEmitter();
    newNanomaterialTypeEmitter =  new EventEmitter();
    editComposingElementShowEmitter =  new EventEmitter();
    editComposingElementIndexEmitter =  new EventEmitter();
    editComposingElementCancelEmitter =  new EventEmitter();

    constructor(){
    }

    setComposingElementsArray(cea){
        this.composingElementsArray = cea;
    }

    getComposingElementsArray(){
        return this.composingElementsArray;
    }

    setNewComposingElementShow(){
        this.newComposingElementShowEmitter.emit(true);
        this.editComposingElementShowEmitter.emit(false);
    }

   setNewComposingElementHide(){
       this.newComposingElementShowEmitter.emit(false);
   }


    setEditComposingElementShow(){
        this.editComposingElementShowEmitter.emit(true);
        this.newComposingElementShowEmitter.emit(false);
    }



    // User has canceled out of Edit
   setEditComposingElementHide( i: number = -1 ){

        if( i >= 0 ){
            this.editComposingElementCancelEmitter.emit(i);
        }

       this.editComposingElementShowEmitter.emit(false);
   }

    editComposingElement( element ){
        this.editComposingElementIndexEmitter.emit( element );
        this.editComposingElementShowEmitter.emit(true);
    }

    getNanomaterialEditData(){
        return this.nanomaterialEditData;
    }

    setNanomaterialEditData( nmsData ){
        this.nanomaterialEditData = nmsData;
    }

    getNanomaterialSetupData(){
        return this.nanomaterialSetupData;
    }

    setNanomaterialSetupData( nmsData ){
        this.nanomaterialSetupData = nmsData;
    }

    getNanoMaterialSampleId(){
        return this.nanomaterialSampleId;
    }

    setNanoMaterialSampleId( nmsId ){
        this.nanomaterialSampleId = nmsId;
    }

    getNanoMaterialDataId(){
        return this.nanomaterialDataId;
    }

    setNanoMaterialDataId( nmdId ){
        this.nanomaterialDataId = nmdId;
    }

    setNanoMaterialSampleDescription( nmsDec ){
        this.nanomaterialDescription = nmsDec;
    }

   getNanoMaterialSampleDescription(  ){
        return this.nanomaterialDescription;
    }


    setNewNanomaterialType( type, sampleId, description ){
        this.newNanomaterialType = type;
        this.newNanomaterialTypeEmitter.emit( {type, sampleId, description });
    }

    getsetNewNanomaterialType(){
        return this.newNanomaterialType;
    }

}
