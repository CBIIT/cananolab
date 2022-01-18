import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../constants';
import { NgForm } from '@angular/forms';
import { ApiService } from '../../../common/services/api.service';
import { HttpClient } from '@angular/common/http';
import { UtilService } from '../../../common/services/util.service';
import { Properties } from '../../../../../assets/properties';

@Component( {
    selector: 'canano-protocol-create',
    templateUrl: './protocol-create.component.html',
    styleUrls: ['./protocol-create.component.scss']
} )
export class ProtocolCreateComponent implements OnInit{
    helpUrl = Consts.HELP_URL_PROTOCOL_CREATE;
    toolHeadingName = 'Create Protocol';

    // List for the dropdown
    protocolTypes = [];
    upload = true;
    fileUrl = false;
    selectedFiles = [];
    externalUrl = '';
    filePath: File;
    fileName = '';
    uri = '';
    theAccess = {'name': ''};
    fileId = 0;
    review = false;
    isPublic = false;
    createdBy = '';
    isOwner = true;
    uriExternal = false;

    // @CHECKME I don't think we would have this for a create id = '0';

    constructor( private apiService: ApiService, private httpClient: HttpClient,
                 private utilService: UtilService ){
    }

    ngOnInit(): void{
        // Get Protocol types from server
        this.init();

    }


    init(){

        // Get list of Protocol types for dropdown
        this.apiService.doGet( Consts.QUERY_PROTOCOL_SETUP, '' ).subscribe(
            data => {
                this.protocolTypes = <any>data['protocolTypes'];
                console.log( 'MHL QUERY_INIT_SETUP: ', this.protocolTypes );
            } );

    }

    async onSubmit( f: NgForm ){
        let haveDupStatus = false;
        let isDup = false;
        f = f.value;
        console.log( 'MHL *********************************** 03 onSubmit: ', f );
        console.log( 'MHL *********************************** 03b onSubmit uri: ', this.uri );
        console.log( 'MHL *********************************** 03c onSubmit externalUrl: ', this.externalUrl );
        console.log( 'MHL **************************** 03d onSubmit encodeURIComponent( this.externalUrl ): ', encodeURIComponent( this.externalUrl ) );
// To check for dups
//   url: '/caNanoLab/rest/protocol/getProtocol?protocolType=' + $scope.protocolForm.type + '&protocolName=' + $scope.protocolForm.name + '&protocolVersion=' + $scope.protocolForm.version


        console.log( 'MHL  BEFORE  this.apiService.doGet' );
// Error while viewing for protocol null
        // ///////////////////////////////////////////////////////
        // ///////////////////////////////////////////////////////
        console.log( 'MHL protocolType=' + f['type'] + '&protocolName=' + f['name'] + '&protocolVersion=' + f['version'] );
        this.apiService.doGet( Consts.QUERY_GET_PROTOCOL, 'protocolType=' + f['type'] + '&protocolName=' + f['name'] + '&protocolVersion=' + f['version'] ).subscribe(
            // It already exists
            data => {
                console.log( 'MHL 00 ZED IS A Dupe: ', data );
                isDup = true;
                haveDupStatus = true;
                alert( 'A database record with the same protocol type and protocol name already exists.  Load it and update?' );
            },

            // It does NOT already exist
            ( err ) => {
                console.log( 'MHL 01 ZED NOT a Dupe: ', err );
                isDup = false;
                haveDupStatus = true;

                return;
            } );

        // Wait until we know if it already exists
        while( !haveDupStatus ){
            await this.utilService.sleep( Consts.waitTime );
        }
        console.log( 'MHL  after  this.apiService.doGet' );
        if( isDup ){
            return;
        }
        // ///////////////////////////////////////////////////////
        // ///////////////////////////////////////////////////////


        let parameters = '';
        Object.keys( f )
            .forEach( key => {
                    console.log( 'MHL Key: ', key + ' - ' + f[key] );
                    if( key === 'fileDescription' ){
                        f[key] = encodeURIComponent( f[key] ); // .replace(/\n/, '%A0' );
                    }

                    if( key === 'createdBy' ){
                        f[key] = Properties.current_user;
                        this.createdBy = Properties.current_user; // @CHECKME  I don't think we use this.
                    }

                    if( key === 'fileName' ){
                        f[key] = this.uri;
                    }

                    if( key === 'externalUrl' ){
                        console.log( 'MHL 100 this.externalUrl: ', this.externalUrl );
                        f[key] = encodeURIComponent( this.externalUrl ); // @CHECKME  Make sure this works.  "=" was getting turned into ":".  %3D works
                        console.log( 'MHL 101 this.externalUrl: ', f[key] );
                    }

                    if( f[key].length > 0 ){
                        parameters += '&' + key + '=' + f[key];
                    }else if( typeof f[key] === 'boolean' ){
                        parameters += '&' + key + '=' + this.utilService.isTrue( f[key] );
                    }else if( typeof f[key] === 'number' ){
                        parameters += '&' + key + '=' + f[key];
                    }else if(key === 'theAccess'){
                       // parameters += '&' + key + '={}';
                        parameters += '&' + key + '=' + f[key];
                    }
                    console.log( 'MHL ERR f[' + key + ']: ', f[key] );
                    console.log( 'MHL  ERR typeof f[' + key + ']: ', typeof f[key] );
                }
            );
        console.log( 'MHL *********************************** 03e onSubmit externalUrl: ', this.externalUrl );
        console.log( 'MHL *********************************** 03f onSubmit Form: ', f );

        console.log( 'ZED ******************************************' );


        // Send the file
        if( this.upload && this.selectedFiles[0] !== undefined ){
            let formData = new FormData();
            console.log( 'MHL ZED  100 this.selectedFiles[0].fileName: ', this.selectedFiles[0].fileName );
            this.selectedFiles[0].fileName = encodeURI( this.selectedFiles[0].fileName );
            console.log( 'MHL ZED  101 this.selectedFiles[0].fileName: ', this.selectedFiles[0].fileName );

            formData.append( 'files', this.selectedFiles[0] );
            console.log( 'MHL ZED  102 formData: ', formData );
            let upload$ = this.httpClient.post( '/caNanoLab/rest/core/uploadFile', formData );
            upload$.subscribe( data => {
                    console.log( 'ZED MHL Good Post file upload: ', data );
                },
                err => {
                    console.error( 'ZED MHL BAD Post file upload: ', err );
                } );
        }
        // END  Send the file

        // Send the new Protocol
        console.log( 'ZED MHL Form: ', f );
        console.log( 'ZED  MHL parameters.substr( 1 ): ', parameters.substr( 1 ) );
        // Do the submit
        this.apiService.doPost( Consts.QUERY_CREATE_PROTOCOL, parameters.substr( 1 ) ).subscribe(
            data => {
                console.log( 'MHL QUERY_CREATE_PROTOCOL: ', data );
                this.externalUrl = decodeURIComponent( this.externalUrl ); // Make it look right in the UI
            },
            err => {
                console.error( 'MHL 702 ERROR doPost QUERY_CREATE_PROTOCOL: ', err.message );
                console.error( 'MHL 703 ERROR doPost QUERY_CREATE_PROTOCOL: ', err );
                this.externalUrl = decodeURIComponent( this.externalUrl ); // Make it look right in the UI
            }
        );

    }


    /**
     * When User uses browser upload function
     * @param e
     */
    getFiles( e ){
        console.log( 'MHL 00 getFiles: ', e );
        console.log( 'MHL 01 getFiles: ', e.target.files );

        this.selectedFiles = e.target.files;
        this.uri = this.selectedFiles[0].name;
    }

    onEnterFileUrl(){
        this.fileUrl = true;
        this.uriExternal = true;
        this.upload = false;
    }

    onUpload(){
        this.upload = true;
        this.uriExternal = false;
        this.fileUrl = false;
    }
}
