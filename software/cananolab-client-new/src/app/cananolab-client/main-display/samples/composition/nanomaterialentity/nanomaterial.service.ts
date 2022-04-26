import { Injectable } from '@angular/core';

@Injectable( {
    providedIn: 'root'
} )
export class NanomaterialService{
    nanomaterialSampleId;
    nanomaterialDataId;
    nanomaterialSetupData;
    nanomaterialEditData;

    constructor(){
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
