// -- This is the one, not ProtocolEditComponent

import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Consts } from '../../../../constants';
import { NgForm } from '@angular/forms';
import { ProtocolsService } from '../protocols.service';
import { ApiService } from '../../../common/services/api.service';
import { StatusDisplayService } from '../../../status-display/status-display.service';
import { HttpClient } from '@angular/common/http';
import { UtilService } from '../../../common/services/util.service';
import { timeout } from 'rxjs/operators';
import { Properties } from '../../../../../assets/properties';
import { Subject } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
@Component( {
    selector: 'canano-protocol-edit-bravo',
    templateUrl: './protocol-edit-bravo.component.html',
    styleUrls: ['./protocol-edit-bravo.component.scss']
} )
export class ProtocolEditBravoComponent implements OnInit, OnDestroy{
    @ViewChild( 'f', { static: false } ) protocolCreateForm: NgForm;

    helpUrl = Consts.HELP_URL_PROTOCOL_EDIT;
    toolHeadingNameSearchResults = 'Update Protocol';
    protocolData;
    protocolId;
    userName;
    type = '';
    protocolTypes = [];
    name;
    abbreviation;
    version;
    externalUrl;
    create_protocol_file_radio;
    fileNameHold = 'XX'
    fileToUpload;
    id;
    uriExternal: boolean;

    properties = Properties;
    consts = Consts;
    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private route:ActivatedRoute,private protocolsService: ProtocolsService, private apiService: ApiService,
                 private statusDisplayService: StatusDisplayService, private httpClient: HttpClient,
                 private utilService: UtilService ){
    }

    ngOnInit(): void{
        this.route.params.subscribe(data=> {
            this.protocolId=data['protocolId'];
            this.getProtocolData();
            this.userName = this.statusDisplayService.getUser(); // @FIXME getUser should not be part of StatusDisplayService
            this.init();

            this.statusDisplayService.updateUserEmitter.pipe( timeout( Properties.HTTP_TIMEOUT ) ).subscribe(
                data => {
                    this.userName = data;
                } );
        })


    }


    getProtocolData(){

        // Get Protocol data from server
        this.apiService.doGet( Consts.QUERY_EDIT_PROTOCOL, 'protocolId=' + this.protocolId ).subscribe(
            data => {
                this.protocolData = data;
                this.fileNameHold = <any>data['fileName'];

                this.uriExternal = <any>data['uriExternal']; // I shouldn't need this, but I couldn't get the radio button to setup correctly
            } );
    }

    init(){

        // Get list of Protocol types for dropdown
        this.apiService.doGet( Consts.QUERY_PROTOCOL_SETUP, '' ).subscribe(
            data => {
                this.protocolTypes = <any>data['protocolTypes'];
            } );

    }

    getFiles( e ){
        this.protocolData['uriExternal'] = false;
        this.uriExternal = false;
        this.protocolData.fileName = e.target.files[0];
        this.fileToUpload = e.target.files[0];
    }

    onEnterFileUrl(){
        this.protocolData['uriExternal'] = true;
        this.uriExternal = true;

    }

    onUpload(){
        this.protocolData['uriExternal'] = false;
        this.uriExternal = false;
    }

    onReset(){
        this.init();
    }

    onSubmit(){
        this.sendFileIfNeeded();
        this.sendUpdateProtocol();
    }

    sendUpdateProtocol(){
        // Do the submit
        this.apiService.doPost( Consts.QUERY_CREATE_PROTOCOL, this.buildParameterString().substr( 1 ) ).subscribe(
            data => {
                console.log( 'QUERY_CREATE_PROTOCOL: ', data ); // protocolData.uriExternal
            },
            err => {
                console.error( 'ERROR doPost QUERY_CREATE_PROTOCOL: ', err );
            }
        );


    }

    deleteProtocol(){
        // Do the submit
        this.apiService.doPost( Consts.QUERY_DELETE_PROTOCOL, this.buildParameterString().substr( 1 ) ).subscribe(
            data => {
                console.log( 'QUERY_DELETE_PROTOCOL: ', data );
            },
            err => {
                console.error( 'ERROR doPost QUERY_DELETE_PROTOCOL: ', err.message );
            }
        );


    }

    sendFileIfNeeded(){
        // Do we need to upload a new file?
        if( this.protocolData.fileName !== this.fileNameHold && (!this.protocolData.uriExternal) ){
            alert( 'Sending file.' );
            let formData = this.buildFileUploadForm();

            let upload$ = this.httpClient.post( '/caNanoLab/rest/core/uploadFile', formData );
            upload$.subscribe( data => {
                    console.log( 'File upload reports: ', data );
                },
                err => {
                    console.error( 'BAD Post file upload: ', err );
                } );
        } // END  Send the file

        // Send an external URI
        else{
            alert( 'NOT Sending file Ext.' );

        }
    }


    buildFileUploadForm(): FormData{
        let formData = new FormData();
        formData.append( 'files', this.fileToUpload );

        formData.append( 'fileName', encodeURI( this.protocolData.fileName['name'] ) );
        formData.append( 'uriExternal', 'false' );
        formData.append( 'review', 'false' );
        formData.append( 'isPublic', 'false' );
        formData.append( 'type', this.type );

        formData.delete( 'externalUrl' );
        return formData;
    }


    buildExternalUriForm(): FormData{
        let formData = new FormData();
        formData.append( 'uriExternal', 'true' );
        formData.append( 'review', 'false' );
        formData.append( 'isPublic', 'false' );
        formData.append( 'type', this.type );
        return formData;
    }


    buildParameterString(): string{
        // Add the parts that are not from the UI form
        let parameters = '';
        parameters += '&createdBy=' + Properties.current_user;
        parameters += '&isPublic=' + false;
        parameters += '&fileId=' + this.protocolData.fileId;
        parameters += '&id=' + this.protocolData.id;
        parameters += '&uriExternal=' + this.uriExternal;
        parameters += '&review=' + false;
        if( !this.uriExternal && (this.fileToUpload !== undefined)){
            parameters += '&uri=' + this.fileToUpload.name;
        }
        // this.protocolCreateForm.value.theAccess = {}; // @CHECKME I don't think we need this (yet)

        if( !this.uriExternal ){
            // Remove from form if we are uploading a file
            this.protocolCreateForm.form.removeControl( 'externalUrl' );
        }

        // Put the data from the form into "parameters"
        Object.keys( this.protocolCreateForm.value )
            .forEach( key => {
                    if( key === 'fileDescription' ){
                        this.protocolCreateForm.value[key] = encodeURIComponent( this.protocolCreateForm.value[key] ); // .replace(/\n/, '%A0' );
                    }

                    if( key === 'externalUrl' ){
                        this.protocolCreateForm.value[key] = encodeURIComponent( this.protocolCreateForm.value[key] ); // @CHECKME  Make sure this works.  "=" was getting turned into ":".  %3D works
                    }

                    if( this.protocolCreateForm.value[key].length > 0 ){
                        parameters += '&' + key + '=' + this.protocolCreateForm.value[key];
                    }else if( typeof this.protocolCreateForm.value[key] === 'boolean' ){
                        parameters += '&' + key + '=' + this.utilService.isTrue( this.protocolCreateForm.value[key] );
                    }else if( typeof this.protocolCreateForm.value[key] === 'number' ){
                        parameters += '&' + key + '=' + this.protocolCreateForm.value[key];
                    }else{
                        console.error( 'Unidentified type: ', key + '=' + this.protocolCreateForm.value[key] );
                    }
                }
            );
        return parameters;
    }

    onDeleteClick(){
        // @TODO  Popup a confirmation dialog
        this.deleteProtocol();
    }
    buildParameterString1(): string{
        let parameters = '';
        Object.keys( this.protocolCreateForm.value )
            .forEach( key => {
                if( this.protocolCreateForm.value[key] !== null ){
                    let temp = this.protocolCreateForm.value[key];
                    if( temp.length > 0 || typeof this.protocolCreateForm.value[key] === 'number' || typeof this.protocolCreateForm.value[key] === 'boolean' ){
                        parameters += '&' + key + '=' + this.protocolCreateForm.value[key];
                    }
                }
            } );
        return parameters;
    }

    buildParameterString0(): string{
        // Add the parts that are not from the UI form
        let parameters = '';
        parameters += '&createdBy=' + Properties.current_user;
        parameters += '&isPublic=' + false;
        parameters += '&fileId=0';
        parameters += '&fileId=0';
        parameters += '&uriExternal=' + this.uriExternal;
        parameters += '&review=' + false;
        if( !this.uriExternal ){
            parameters += '&uri=' + this.protocolData.fileName;
        }
        // this.protocolCreateForm.value.theAccess = {}; // @CHECKME I don't think we need this (yet)

        if( !this.uriExternal ){
            // Remove from form if we are uploading a file
            this.protocolCreateForm.form.removeControl( 'externalUrl' );
        }

        // Put the data from the form into "parameters"
        Object.keys( this.protocolCreateForm.value )
            .forEach( key => {
                    if( key === 'fileDescription' ){
                        this.protocolCreateForm.value[key] = encodeURIComponent( this.protocolCreateForm.value[key] ); // .replace(/\n/, '%A0' );
                    }

                    if( key === 'externalUrl' ){
                        this.protocolCreateForm.value[key] = encodeURIComponent( this.externalUrl ); // @CHECKME  Make sure this works.  "=" was getting turned into ":".  %3D works
                    }

                    if( this.protocolCreateForm.value[key].length > 0 ){
                        parameters += '&' + key + '=' + this.protocolCreateForm.value[key];
                    }else if( typeof this.protocolCreateForm.value[key] === 'boolean' ){
                        parameters += '&' + key + '=' + this.utilService.isTrue( this.protocolCreateForm.value[key] );
                    }else if( typeof this.protocolCreateForm.value[key] === 'number' ){
                        parameters += '&' + key + '=' + this.protocolCreateForm.value[key];
                    }
                }
            );

        return parameters;
    }

// ////////////////////////////////////////////////////////////////////

    // FIXME - this function is a DUPE  We need constants for this
    openWindow( pageURL, name, width = 700, height = 900 ){
        window.open( pageURL, name, 'alwaysRaised,dependent,toolbar,status,scrollbars,resizable,width=' + width + ',height=' + height );
    }

    ngOnDestroy(): void{
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

}
