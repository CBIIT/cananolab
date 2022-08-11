import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Consts } from '../../../../constants';
import { ApiService } from '../../../common/services/api.service';
import { UtilService } from '../../../common/services/util.service';
import { Router, Params, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
@Component( {
    selector: 'canano-protocol-create',
    templateUrl: './protocol-create.component.html',
    styleUrls: ['./protocol-create.component.scss']
} )
export class ProtocolCreateComponent implements OnInit, AfterViewInit{
    accessIndex;
    currentRoute='protocol-create';
    data;
    defaultProtocolType = 'radio labeling'; // Find a way to set this in time to protocolTypes[0]
    downloadUrl=Consts.QUERY_DOWNLOAD_FILE;
    errors;
    existingData;
    externalUrl;
    fileToUpload;
    haveDupStatus = false;
    toolHeadingName='Create Protocol';
    isDup = false;
    message;
    protocolId;
    recipientList;
    setupData={};
    theAccess={};
    submitReviewButton=true;
    constructor(private httpClient:HttpClient, private route:ActivatedRoute,private router:Router,private apiService: ApiService, private utilService: UtilService,
                 ){
    }

    ngOnInit(): void{
        this.route.params.subscribe(
            ( params: Params ) => {
                this.protocolId=params['protocolId'];
                if (params['message']) {
                    if (params['message']=='deleted') {
                        this.message='Protocol Deleted Succesfully'
                    }
                    else {
                        this.message='Protocol Created Successfully'
                    }
                }
                this.init();
            });

        this.errors={};
    }

    addAccess() {
        this.accessIndex=-1;
        this.recipientList=null;
        this.theAccess={
                "accessType":"",
                "recipient":"",
                "roleName":"",
                "recipientDisplayName":""
            }
    }


    buildExternalUriForm(): FormData{
        let formData = new FormData();
        formData.append( 'uriExternal', 'true' );
        formData.append( 'review', 'false' );
        formData.append( 'isPublic', 'false' );
        formData.append( 'type', this.data.type );
        return formData;
    };

    cancelAccess() {
        this.accessIndex=null;
    }

    uploadFile(event) {
        this.fileToUpload = new FormData();
        const tFile = event.target.files.item(0);
        this.fileToUpload.append('myFile', tFile, tFile.name);
        this.fileToUpload.append( 'uriExternal', 'false' );
        this.fileToUpload.append( 'review', 'false' );
        this.fileToUpload.append( 'isPublic', 'false' );
        this.fileToUpload.append( 'type', this.data.type );
        console.log(this.fileToUpload)
    }

    delete() {
        if (confirm("Are you sure you wish to delete this protocol?")) {
            this.apiService.doPost(Consts.QUERY_DELETE_PROTOCOL,this.data).subscribe(data=> {
                this.router.navigate(['home/protocols/protocol-create/deleted'])
            },
            errors=> {
                this.errors=errors;
            })
        }
    }

    showDeleteButton() {
        if (this.router.url.includes('edit-protocol')) {
            return true
        }
        return false
    }

    changeAccessType(event) {
        this.recipientList=null;
        this.theAccess['recipient']='';
        this.theAccess['roleName']='';
        if (event=='role') {
            this.theAccess['recipientDisplayName']='Public';
            this.theAccess['recipient']='ROLE_ANONYMOUS';
            this.theAccess['roleName']="R";
        };
    };

    deleteAccess() {
        if (confirm("Are you sure you wish to delete this " + this.theAccess['accessType'] + "?")) {
            this.data['theAccess']=this.theAccess;
            let url = this.apiService.doPost(Consts.QUERY_PROTOCOL_DELETE_ACCESS,this.data);
            url.subscribe(data=>{
                this.data=data;
                this.accessIndex=null;
                this.errors={};
            },
            error=> {
                this.errors=error;
            })
        }

    }

    duplicateCheck(){
        let formValues = this.data;
        let dupQuery = '';
        if( formValues['version'] === undefined ){
            dupQuery = 'protocolType=' + formValues['type'] + '&protocolName=' + formValues['name'];
        }else{
            dupQuery = 'protocolType=' + formValues['type'] + '&protocolName=' + formValues['name'] + '&protocolVersion=' + formValues['version'];
        }
        this.apiService.doGet( Consts.QUERY_GET_PROTOCOL, dupQuery ).subscribe(
            // It already exists
            data => {
                this.existingData=data;
                this.errors={};
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

    };

    editAccess(index,access) {
        console.log(index,access)
        this.accessIndex=index;
        this.recipientList=null;
        this.theAccess=JSON.parse(JSON.stringify(access));
    };

    getRecipientList() {
        var url;
        if (this.theAccess['accessType']=='group') {
            url=this.apiService.doGet(Consts.QUERY_GET_COLLABORATION_GROUPS,'searchStr=');
        }
        if (this.theAccess['accessType']=='user') {
            url=this.apiService.doGet(Consts.QUERY_GET_USERS,'searchStr=');
        }
        url.subscribe(data=> {
            this.recipientList=data;
            this.errors={};
        },
        error=> {
            this.errors=error;

        })
    };

    init(){
        if (this.protocolId) {
            this.currentRoute='edit-protocol';
            this.toolHeadingName='Edit Protocol';
            this.apiService.doGet( Consts.QUERY_EDIT_PROTOCOL, 'protocolId='+this.protocolId ).subscribe(
                data => {
                    this.data = data;
                },
                errors=> {
                    this.errors=errors;
                } );
        }
        else {
            this.toolHeadingName='Create Protocol';
            this.data={
                "uriExternal":false,
                "type":"",
                "name":"",
                "abbreviation":"",
                "version":"",
                "fileTitle":"",
                "fileDescription":""
            }
        }
        // Get list of Protocol types for dropdown
        this.apiService.doGet( Consts.QUERY_PROTOCOL_SETUP, '' ).subscribe(
            data => {
                this.errors={};
                this.setupData = data;
                // this.defaultProtocolType = this.protocolTypes[0]; // SET default - This doesn't work @CHECKME  I had to hard code the default
            },
            errors=> {
                this.errors=errors;
            } );

    }

    onReset(){
        this.init();
    }


    async onSubmit(){
        // ////////////////////////////////////////////////////////
        // Check for dups
        if (!this.protocolId) {
            this.haveDupStatus = false;
            console.log('duplicate check?')
            this.duplicateCheck();
            // Wait until we know if it already exists
            while( !this.haveDupStatus ){
                await this.utilService.sleep( Consts.waitTime );
            }
            if( this.isDup ){
                if (confirm("A database record with the same protocol type and protocol name already exists. Load it and update?")) {
                    this.router.navigate(['home/protocols/edit-protocol',this.existingData['id']]);
                }
                return;
            }

        }


        // ////////////////////////////////////////////////////////
        // Do we need to send a file?
        // Send the file
        if( !this.data.uriExternal ){
            if (this.fileToUpload) {
            let uploadUrl = this.httpClient.post('/'+Consts.QUERY_UPLOAD_FILE, this.fileToUpload);
            uploadUrl.subscribe(data=> {
                    this.errors={};
                    this.data.fileId="0";
                    this.data.uri=data['fileName'];
                    this.submitProtocol();
            },
            error=> {

            })
            }
            else {
                // we dont have a file //
                this.submitProtocol();

            }

        } // END  Send the file

          // Send an external URI
        else{
            let formData = this.buildExternalUriForm();
            this.data.uriExternal=true;
            this.submitProtocol();
        }
    };



    ngAfterViewInit(): void{
        //  this.defaultProtocolType = this.protocolTypes[0]; // SET default - This doesn't work @CHECKME  I had to hard code the default
    };

    onEnterFileUrl(){
        this.data.uriExternal = true;
    };

    onUpload(){
        this.data.uriExternal = false;
    };

    saveAccess() {
        this.data['theAccess']=this.theAccess;
        let url=this.apiService.doPost(Consts.QUERY_PROTOCOL_SAVE_ACCESS,this.data);
        url.subscribe(data=> {
            this.data=data;
            this.errors={};
            if (this.fileToUpload) {
                this.onSubmit();
            }
            else {
                if (this.currentRoute=='protocol-create') {
                    this.router.navigate(['home/protocols/edit-protocol',data['id'],'success'])
                }
            };
        },
        error=> {
            this.errors=error;
        });
        this.accessIndex=null;
    }

    submitProtocol() {
        this.apiService.doPost( Consts.QUERY_CREATE_PROTOCOL, this.data ).subscribe(
            data => {
                this.errors={};
                this.data.theAccess=this.theAccess;
                this.externalUrl = decodeURIComponent( this.externalUrl ); // Make it look right in the UI
                let protocolId=data.replaceAll('"','').replace('[','').replace(']','').split(',')[1];
                if (this.currentRoute=='protocol-create') {
                    this.router.navigate(['home/protocols/edit-protocol',protocolId,'success'])
                }
                else {
                    this.message='Protocol Successfully Updated';
                }
            },
            err => {
                this.errors=err;
                this.externalUrl = decodeURIComponent( this.externalUrl ); // Make it look right in the UI
            }
        );
    }

    getProtocol(event) {
        let url = this.apiService.doGet(Consts.QUERY_GET_PROTOCOL,'protocolType='+this.data.type+'&protocolName='+this.data.name+'&protocolVersion='+this.data.version);
        url.subscribe(data=> {
            if (confirm("A database record with the same protocol type and protocol name already exists. Load it and update?")) {
                this.router.navigate(['home/protocols/edit-protocol',data['id']])
            }
        })
    }

    submitForReview() {
        console.log(this.data)
        let url = this.apiService.doPost(Consts.QUERY_PROTOCOL_SUBMIT_REVIEW,{dataId:this.data.id,dataName:this.data.name,dataType:"protocol"},'text');
        url.subscribe(data=> {
            this.submitReviewButton=false;
        })
    }

}
