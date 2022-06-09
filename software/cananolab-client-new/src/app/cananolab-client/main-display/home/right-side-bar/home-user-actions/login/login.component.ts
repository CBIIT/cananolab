import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../../../common/services/api.service';
import { StatusDisplayService } from '../../../../../status-display/status-display.service';
import { Router } from '@angular/router';
import { IdleService } from 'src/app/cananolab-client/common/components/idle/idle.service';
@Component( {
    selector: 'canano-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
} )
export class LoginComponent implements OnInit{

    user = '';
    password = '';
    homePage = true;
    loaded=false;
    constructor( private idleService:IdleService,private router:Router,private apiService: ApiService, private statusDisplayService: StatusDisplayService ){
    }

    ngOnInit(): void{
        if (this.router.url.includes('login'))
        {
            this.homePage=false;
            this.loaded=true;
        }
    }

    onLoginClick(){
        this.apiService.authenticateUser( this.user, this.password )
        this.statusDisplayService.updateUser( this.user );
        this.idleService.startTimer();
        this.router.navigateByUrl('home');
    }
}
