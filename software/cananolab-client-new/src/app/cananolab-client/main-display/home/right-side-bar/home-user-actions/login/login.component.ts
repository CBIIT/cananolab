import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../../../common/services/api.service';
import { StatusDisplayService } from '../../../../../status-display/status-display.service';

@Component( {
    selector: 'canano-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
} )
export class LoginComponent implements OnInit{

    user = '';
    password = '';

    constructor( private apiService: ApiService, private statusDisplayService: StatusDisplayService ){
    }

    ngOnInit(): void{
    }

    onLoginClick(){
        console.log( 'MHL User: ', this.user );
        console.log( 'MHL password: ', this.password );

        this.apiService.authenticateUser( this.user, this.password )
        this.statusDisplayService.updateUser( this.user );
    }
}
