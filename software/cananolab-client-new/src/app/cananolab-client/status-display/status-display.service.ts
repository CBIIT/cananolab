import { EventEmitter, Injectable } from '@angular/core';
import { Consts } from '../../constants';
import { ApiService } from '../common/services/api.service';
import { Properties } from '../../../assets/properties';

@Injectable( {
    providedIn: 'root'
} )
export class StatusDisplayService{

    updateUserEmitter = new EventEmitter();
    user = '';

    constructor( private apiService: ApiService ){
    }

    getUser(){
        return this.user;
    }

    updateUser( user ){
        this.user = user;
        this.updateUserEmitter.emit( user );
        this.updateUserGroups();
    }

    updateUserGroups(){

        //  this.apiService.doPost( Consts.QUERY_SEARCH, 'keyword=' + this.topKeyWordSearchText ).subscribe(
        this.apiService.doGet( Consts.QUERY_GET_USER_GROUPS, '' ).subscribe(
            data => {
                // Set user as "Logged in"
                Properties.logged_in = true;
                console.log(data);
            },
            ( err ) => {
                console.log( 'ERROR getUserGroups: ', err );
            } );

    }

}
