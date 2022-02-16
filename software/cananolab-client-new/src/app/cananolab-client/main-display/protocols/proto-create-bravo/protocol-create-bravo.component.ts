import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component( {
    selector: 'canano-protocol-create-bravo',
    templateUrl: './protocol-create-bravo.component.html',
    styleUrls: ['./protocol-create-bravo.component.scss']
} )
export class ProtocolCreateBravoComponent implements OnInit{


    @ViewChild( 'f', { static: false } ) signupForm: NgForm;
    defaultQuestion = 'teacher';
    answer = '';
    genders = ['male', 'female'];
    user = {
        username: '',
        email: '',
        secretQuestion: '',
        answer: '',
        gender: ''
    };
    submitted = false;
    myGroup: any;



    SERVER_URL = 'http://192.168.1.25:8080/cgi-bin/cyberPowerCgi_02.pl';
    creatProtocolFormBuilder: FormGroup;
    protocolTypes = [];
    upload = true;
    fileUrl = false;
    selectedFiles = [];
    externalUrl = '';
    filePath: File;
    fileName = '';
    createdBy = '';
    isOwner = true;

    uriExternal = false;
    review = false;
    theAccess = {};
    isPublic = false;
    type = '';
    name = '';
    version = '';
    fileTitle = '';
    fileDescription = '';
    uri = 'TheUri';
    fileId = 0;



    constructor( private formBuilder: FormBuilder, private httpClient: HttpClient ){
    }

    suggestUserName(){
        const suggestedName = 'Superuser';
        // this.signupForm.setValue({
        //   userData: {
        //     username: suggestedName,
        //     email: ''
        //   },
        //   secret: 'pet',
        //   questionAnswer: '',
        //   gender: 'male'
        // });
        this.signupForm.form.patchValue( {
            userData: {
                username: suggestedName
            }
        } );
    }

    // onSubmit(form: NgForm) {
    //   console.log(form);
    // }

    onSubmit( f: NgForm ){

        this.submitted = true;
        this.user.username = this.signupForm.value.userData.username;
        this.user.email = this.signupForm.value.userData.email;
        this.user.secretQuestion = this.signupForm.value.secret;
        this.user.answer = this.signupForm.value.questionAnswer;
        this.user.gender = this.signupForm.value.gender;

        // this.signupForm.reset();
    }

    ngOnInit(){
        this.creatProtocolFormBuilder = this.formBuilder.group( {} );
    }

    //   onClickMe(e: HTMLFormElement){
    onClickMe( e ){
        console.log( 'onClickMe: ', e );
    }


    onFileSelect( event ){
        if( event.target.files.length > 0 ){
            const file = event.target.files[0];
            this.creatProtocolFormBuilder.get( 'filePicker' ).setValue( file );
            this.creatProtocolFormBuilder.get( 'uri' ).setValue( file );
        }
    }

    /**  NOT USED
     * When User uses browser upload function
     * @param e
     */
    getFiles( e ){
        this.selectedFiles = e.target.files;
        this.uri = this.selectedFiles[0].name;
    }

    onSubmit0(){
        let userForm = new FormGroup( {
            uri: new FormControl(),
            filePicker: new FormControl()
        } );


        let formData = new FormData();
        formData.append( 'file', (this.creatProtocolFormBuilder.get( 'filePicker' ).value) );
        formData.append( 'uri', (this.creatProtocolFormBuilder.get( 'uri' ).value)['name'] );
        formData.append( 'review', (this.creatProtocolFormBuilder.get( 'review' ).value)['name'] );
        formData.append( 'uriExternal', (this.creatProtocolFormBuilder.get( 'uriExternal' ).value)['name'] );
        formData.append( 'theAccess', '{}' );
        formData.append( 'isPublic', (this.creatProtocolFormBuilder.get( 'isPublic' ).value) );

        this.httpClient.post<any>( this.SERVER_URL, formData ).subscribe(
            ( res ) => console.log( res ),
            ( err ) => console.log( err )
        );
    }
}
