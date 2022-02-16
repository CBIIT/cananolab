import { Component, OnDestroy, OnInit } from '@angular/core';
import { Consts } from '../../../../constants';
import { ProtocolsService } from '../protocols.service';
import { Subject } from 'rxjs';
import { ApiService } from '../../../common/services/api.service';
import { NgForm } from '@angular/forms';
import { Properties } from '../../../../../assets/properties';
import { timeout } from 'rxjs/operators';
import { StatusDisplayService } from '../../../status-display/status-display.service';
import { DATA_TYPE_ACCESS_MANAGE } from '../../../common/components/manage-accessibility/manage-accessibility-group/manage-accessibility-group.component';
import { HttpClient } from '@angular/common/http';
import { UtilService } from '../../../common/services/util.service';

@Component( {
    selector: 'canano-protocol-edit',
    templateUrl: './protocol-edit.component.html',
    styleUrls: ['./protocol-edit.component.scss']
} )

/*
*   "type":"physico-chemical assay",
*   "name":"w",
*   "abbreviation":"wwX",
*   "version":"www",
*   "fileId":98435073,
*   "id":98402305,
*  "fileTitle":"wwww",
    "fileName":"protocols/20210913_07-34-28-260_AA_test_1.txt", @CHECKME I don't think we need the file name for the Protocol, I think that's taken care of in the file upload
*  "fileDescription":"wwwww",
    "protectedData":"",  @CHECKME
*   "isPublic":false,
*   "isOwner":true,
    "ownerName":"",
*   "createdBy":"canano_curator",
    "createdDate":1630054798000,
    "userDeletable":true,
    "errors":null,
    "userUpdatable":true,

    "uri":"protocols/20210913_07-34-28-260_AA_test_1.txt",
    "uriExternal":"false",
    "externalUrl":null,
    "review":false,
    "newFileData":null
*/

export class ProtocolEditComponent implements OnInit, OnDestroy{
    createdBy = 'ABC';
    fileId = 'X';
    id = 'x';
    fileName = 'X';
    fileNameHold = 'XX'
    externalUrl = '';
    isPublic = false;
    isOwner = true;
    uriExternal = 'X';
    review = 'X';

    protocolData;
    helpUrl = Consts.HELP_URL_PROTOCOL_EDIT;
    toolHeadingNameSearchResults = 'Update Protocol';
    protocolId;

    displayProtocolGroupAccessesData = [];
    protocolTypes = [];
    protocolCsmRoles = [];
    showGroupAccessEdit = [];
    temp = '';
    protocolShowFileUploader;
    userName = 'TEST User';
    dataType = DATA_TYPE_ACCESS_MANAGE.PROTOCOL;
    manageAccessibilityShow = { dog: 'BlackDog', val: false };

    properties = Properties;

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private protocolsService: ProtocolsService, private apiService: ApiService,
                 private statusDisplayService: StatusDisplayService, private httpClient: HttpClient,
                 private utilService: UtilService ){
    }

    ngOnInit(): void{
        this.protocolId = this.protocolsService.getCurrentProtocolInfo();

        this.getProtocolData();
        this.statusDisplayService.updateUserEmitter.pipe( timeout( Properties.HTTP_TIMEOUT ) ).subscribe(
            data => {
                this.userName = data;
            } );

        // Get list of Protocol types & Csm roles for dropdowns if we don't already have it.
        if( (Properties.PROTOCOL_TYPES.length < 1) || (Properties.PROTOCOL_CSM_ROLES.length < 1) ){
            this.apiService.doGet( Consts.QUERY_PROTOCOL_SETUP, '' ).subscribe(
                data => {
                    this.protocolTypes = <any>data['protocolTypes'];
                    this.protocolCsmRoles = <any>data['csmRoleNames'];
                    Properties.PROTOCOL_TYPES = this.protocolTypes; // Cache it
                    Properties.PROTOCOL_CSM_ROLES = this.protocolCsmRoles; // Cache it
                } );
        }else{
            this.protocolTypes = Properties.PROTOCOL_TYPES;
        }

        if( (this.protocolData !== undefined) && (this.protocolData.uriExternal !== undefined) ){
            this.protocolShowFileUploader = this.protocolData.uriExternal;
        }
    }

    // @TODO
    // Group access stuff
    displayProtocolDataInit(){
        // this.displayProtocolData
        // displayProtocolGroupAccessesData
        // protocolData.groupAccesses
        for( let n = 0; n < this.protocolData.groupAccesses.length; n++ ){
            this.displayProtocolGroupAccessesData.push( this.protocolData.groupAccesses[n] );
            this.displayProtocolGroupAccessesData.push( 'ZZZ' );
            this.showGroupAccessEdit.push( false ); // even
        }
    }

    // 0 = Upload   1 = External URL
    onProtocolFileRadioClick( n ){
        if( n === 0 ){
            this.protocolData.uriExternal = false;
        }else{
            this.protocolData.uriExternal = true;
        }
        this.protocolShowFileUploader = (n === 0);
    }

    getProtocolData(){

        // Get Protocol data from server
        this.apiService.doGet( Consts.QUERY_EDIT_PROTOCOL, 'protocolId=' + this.protocolId ).subscribe(
            data => {
                this.protocolData = data;

                this.createdBy = this.protocolData.createdBy;

                this.fileNameHold = this.protocolData.fileName;
                this.externalUrl = this.protocolData.fileName;
                this.fileName = this.protocolData.fileName;
                this.id = this.protocolData.id;
                this.uriExternal = this.protocolData.uriExternal;
                this.review = this.protocolData.review;

                this.temp = this.protocolData.type;
                this.protocolShowFileUploader = this.protocolData.uriExternal;
                this.displayProtocolDataInit();
            } );
    }

    onSubmit( f: NgForm ){

        // Do we need to upload a new file?
        if( this.protocolData.fileName !== this.fileNameHold && (!this.protocolData.uriExternal) ){
            let formData = new FormData();
            // Encode filename

            formData.append( 'files', '[' + encodeURI( this.protocolData.fileName ) + ']');
            let upload$ = this.httpClient.post( '/caNanoLab/rest/core/uploadFile', formData );
            upload$.subscribe( data => {
                    console.log( 'Good Post file upload: ', data );
                },
                err => {
                    console.error( 'BAD Post file upload: ', err );
                } );


        }else{
            // console.log( 'NOT A NEW FILE' );
        }


        let parameters = '';
        Object.keys( f.value )
            .forEach( key => {

                let temp = f.value[key];
                if( temp.length > 0 || typeof f.value[key] === 'number' || typeof f.value[key] === 'boolean' ){
                    parameters += '&' + key + '=' + f.value[key];
                }
            } );

        // Do the submit
        this.apiService.doPost( Consts.QUERY_CREATE_PROTOCOL, parameters.substr( 1 ) ).subscribe(
            data => {
                console.log( 'QUERY_CREATE_PROTOCOL: ', data ); // protocolData.uriExternal
            },
            err => {
                console.error( 'ERROR doPost QUERY_CREATE_PROTOCOL: ', err.message );
            }
        );


    }


    getFiles( e ){
        this.protocolData.fileName = e.target.files;
    }

    onProtocolGroupAccessEditClick( i ){
        this.showGroupAccessEdit[i] = (!this.showGroupAccessEdit[i]);
        this.showGroupAccessEdit[i + 1] = (!this.showGroupAccessEdit[i + 1]);
    }

    onProtocolRestClick(){
        this.getProtocolData();
    }

    // FIXME - this function is a DUPE  We need constants for this
    openWindow( pageURL, name, width = 700, height = 900 ){
        window.open( pageURL, name, 'alwaysRaised,dependent,toolbar,status,scrollbars,resizable,width=' + width + ',height=' + height );
    }

    ngOnDestroy(): void{
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

}
