import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../common/services/api.service';
import { Consts } from '../../../constants';
import { Properties } from '../../../../assets/properties';
import { StatusDisplayService } from '../../status-display/status-display.service';
import { Router } from '@angular/router';
import { UtilService } from '../../common/services/util.service';

@Component({
  selector: 'canano-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit{

    constructor( private apiService: ApiService, private statusDisplayService: StatusDisplayService,
                 private router: Router, private utilService: UtilService ){
    }

    ngOnInit(): void{
        this.logOut();
        this.router.navigate( [this.utilService.getRouteByName( 'HOME' )] );

    }


    logOut()
    {
        this.apiService.doPost( Consts.QUERY_LOGOUT, '' ).subscribe(
            data => {
                Properties.LOGGED_IN = false;
                this.statusDisplayService.updateUser( 'guest' );

                console.log( 'MHL LogoutComponent: ', data );
            },
            err => {
                this.statusDisplayService.updateUser( 'unknown' ); // CHECKME
                console.error('MHL ERROR doPost Consts.QUERY_LOGOUT: ', err);
            }
        );
    }

}
