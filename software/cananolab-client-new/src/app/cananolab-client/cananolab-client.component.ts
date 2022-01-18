// ----------------------------------------------------------------------------------------
// ---------------------            "cananolab-client"             ------------------------
// ----------           The top level UI component                                   ------
// ----------               Header                                                   ------
// ----------               Main Top Menu                                            ------
// ----------               Navigation Menu                                          ------
// ----------               Static Left side Menu                                    ------
// ----------------------------------------------------------------------------------------
import { Component, OnInit } from '@angular/core';
import { ConfigurationService } from './common/services/configuration.service';
import { Properties } from '../../assets/properties';
import { Router } from '@angular/router';
import { UtilService } from './common/services/util.service';

@Component( {
    selector: 'canano-cananolab-client',
    templateUrl: './cananolab-client.component.html',
    styleUrls: ['./cananolab-client.component.scss']
} )
export class CananolabClientComponent implements OnInit{

    properties = Properties;

    constructor( private configurationService: ConfigurationService, private router: Router,
                 private utilService: UtilService ){
    }

    ngOnInit(): void{
      //  this.init();
    }

    async init(){
        // @FIXME - @TODO change this to a "We are initialized" flag
        //  await this.utilService.sleep( 5000 );
        //  console.log('MHL Set route to /home');
        // this.router.navigate(['home']);
    }
}
