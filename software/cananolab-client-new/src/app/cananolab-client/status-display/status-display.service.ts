import { EventEmitter, Injectable } from '@angular/core';
import { Consts } from '../../constants';
import { ApiService } from '../common/services/api.service';
import { Properties } from '../../../assets/properties';
import { Router } from '@angular/router';
@Injectable( {
    providedIn: 'root'
} )
export class StatusDisplayService{

    updateUserEmitter = new EventEmitter();
    updateGroupEmitter = new EventEmitter();
    user = '';
    groups = [];

    constructor( private router:Router, private apiService: ApiService ){
    }

    getUser(){
        return this.user;
    }

    isEditUrl() {
        console.log(this.router.url)
        return !this.router.url.includes('view');
    }

    updateUser( user ){
        this.user = user;
        this.updateUserGroups();
        this.updateUserEmitter.emit( user );
    }

    updateUserGroups(){
        setTimeout(()=> {
        //  this.apiService.doPost( Consts.QUERY_SEARCH, 'keyword=' + this.topKeyWordSearchText ).subscribe(
            this.apiService.doGet( Consts.QUERY_GET_USER_GROUPS, '' ).subscribe(
                data => {
                    // Set user as "Logged in"
                    Properties.logged_in = true;
                    this.updateGroupEmitter.emit(data)
                },
                ( err ) => {
                    console.log( 'ERROR getUserGroups: ', err );
                } );
        },200)


    }

}
