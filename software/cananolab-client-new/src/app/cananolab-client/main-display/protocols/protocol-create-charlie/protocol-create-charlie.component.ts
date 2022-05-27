import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Consts } from '../../../../constants';
import { ApiService } from '../../../common/services/api.service';
import { NgForm } from '@angular/forms';
import { UtilService } from '../../../common/services/util.service';
import { HttpClient } from '@angular/common/http';
import { Properties } from '../../../../../assets/properties';

@Component( {
    selector: 'canano-protocol-create-charlie',
    templateUrl: './protocol-create-charlie.component.html',
    styleUrls: ['./protocol-create-charlie.component.scss']
} )
export class ProtocolCreateCharlieComponent implements OnInit, AfterViewInit{
    @ViewChild( 'f', { static: false } ) protocolCreateForm: NgForm;

    // List for the dropdown
    protocolTypes = [];
    defaultProtocolType = 'radio labeling'; // Find a way to set this in time to protocolTypes[0]
    data;
    name = ''; // protocolName = '';
    uriExternal: boolean = false;
    externalUrl;
    fileName = '';
    type = '';
    fileToUpload;
    createdBy = '';
    version = '';
    abbreviation = '';
    fileTitle = '';
    fileDescription = '';

    isDup = false;
    haveDupStatus = false;

    constructor( private apiService: ApiService, private utilService: UtilService,
                 private httpClient: HttpClient ){
    }

    ngOnInit(): void{
        this.init();
    }

    duplicateCheck(){
        let formValues = this.protocolCreateForm.value;
        let dupQuery = '';
        if( formValues['version'] === undefined ){
            dupQuery = 'protocolType=' + formValues['type'] + '&protocolName=' + formValues['protocolName'];
        }else{
            dupQuery = 'protocolType=' + formValues['type'] + '&protocolName=' + formValues['protocolName'] + '&protocolVersion=' + formValues['version'];
        }
        this.apiService.doGet( Consts.QUERY_GET_PROTOCOL, dupQuery ).subscribe(
            // It already exists
            data => {
                this.isDup = true;
                this.haveDupStatus = true;
                return true;
            },

            // It does NOT already exist
            ( err ) => {
                this.isDup = false;
                this.haveDupStatus = true;
                return false;
            } );

    }


    buildFileUploadForm(): FormData{
        let formData = new FormData();
        formData.append( 'files', this.fileToUpload );
        formData.append( 'uriExternal', 'false' );
        formData.append( 'review', 'false' );
        formData.append( 'isPublic', 'false' );
        formData.append( 'type', this.type );


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

    async onSubmit(){

        // ////////////////////////////////////////////////////////
        // Check for dups
        this.haveDupStatus = false;
        this.duplicateCheck();
        // Wait until we know if it already exists
        while( !this.haveDupStatus ){
            await this.utilService.sleep( Consts.waitTime );
        }
        if( this.isDup ){
            alert( 'A database record with the same protocol type and protocol name already exists.  Load it and update?' ); // @TODO
            return;
        }

        // ////////////////////////////////////////////////////////
        // Do we need to send a file?
        // Send the file
        if( !this.uriExternal ){
            alert( 'Sending file.' );
            let formData = this.buildFileUploadForm();

            // @CHECKME Should this be done here
            this.fileToUpload.fileName = encodeURI( this.fileToUpload.fileName );
            formData.delete( 'externalUrl' );


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
            let formData = this.buildExternalUriForm();
        }


        // -----  Add missing things to the form  -----
        // Add user
        // this.protocolCreateForm.form.patchValue( { 'createdBy': Properties.current_user } ); // @CHECKME This does not work (I think it should)


        // Add the parts that are not from the UI form
        let parameters = this.buildPerimeterString();

        // ///////////////////////////////////////////////////////

        // Send the new Protocol
        // Do the submit
        this.apiService.doPost( Consts.QUERY_CREATE_PROTOCOL, parameters.substr( 1 ) ).subscribe(
            data => {
                this.externalUrl = decodeURIComponent( this.externalUrl ); // Make it look right in the UI
            },
            err => {
                this.externalUrl = decodeURIComponent( this.externalUrl ); // Make it look right in the UI
            }
        );
        // ///////////////////////////////////////////////////////
    }

    buildPerimeterString(): string{
        // Add the parts that are not from the UI form
        let parameters = '';
        parameters += '&createdBy=' + Properties.current_user;
        parameters += '&isPublic=' + false;
        parameters += '&fileId=0';
        parameters += '&uriExternal=' + this.uriExternal;
        parameters += '&review=' + false;
        if( ! this.uriExternal){
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

    /**
     * When User uses browser upload function
     * @param e
     */
    getFiles( e ){
        this.fileToUpload = e.target.files[0];
    }

    onReset(){
        this.protocolCreateForm.reset();
        this.protocolCreateForm.form.patchValue( { type: 'safety' } ); // @TODO - this doesn't work
        this.type = 'safety';
    }

    init(){
        this.data={
            "protocolType":"",
            "protocolName":"",
            "abbreviation":"",
            "version":"",
            "fileTitle":"",
            "fileDescription":""
        }
        // Get list of Protocol types for dropdown
        this.apiService.doGet( Consts.QUERY_PROTOCOL_SETUP, '' ).subscribe(
            data => {
                this.protocolTypes = <any>data['protocolTypes'];
                // this.defaultProtocolType = this.protocolTypes[0]; // SET default - This doesn't work @CHECKME  I had to hard code the default
            } );

    }

    ngAfterViewInit(): void{
        //  this.defaultProtocolType = this.protocolTypes[0]; // SET default - This doesn't work @CHECKME  I had to hard code the default

    }

    onEnterFileUrl(){
        this.uriExternal = true;
    }

    onUpload(){
        this.uriExternal = false;
    }
}
