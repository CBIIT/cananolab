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
import { ApiService } from './common/services/api.service';
import { TopMainMenuService } from './top-main-menu/top-main-menu.service';
import { StatusDisplayService } from './status-display/status-display.service';
@Component( {
    selector: 'canano-cananolab-client',
    templateUrl: './cananolab-client.component.html',
    styleUrls: ['./cananolab-client.component.scss']
} )
export class CananolabClientComponent implements OnInit{

    properties = Properties;

    constructor( private statusDisplayService:StatusDisplayService,private topMainMenuService:TopMainMenuService,private apiService:ApiService,private configurationService: ConfigurationService, private router: Router,
                 private utilService: UtilService ){
    }

    ngOnInit(): void{
        let loginUrl=this.apiService.doGet('caNanoLab/rest/security/getUserGroups',{});
        loginUrl.subscribe(data=> {
            let keys=Object.keys(data);
            console.log('hi')
            if (keys[0]!='anonymousUser') {
                this.properties['LOGGED_IN']=true;
                this.properties['logged_in']=true;
                this.properties['current_user']=keys[0];
                this.statusDisplayService.updateUser(this.properties['current_user'])

                this.topMainMenuService.showOnlyMenuItems(
                    [
                        'HOME',
                        'WORKFLOW',
                        'PROTOCOLS',
                        'SAMPLES',
                        'PUBLICATIONS',
                        'GROUPS',
                        'CURATION',
                        'MY_WORKSPACE',
                        'MY_FAVORITES',
                        'LOGOUT'
                    ]
                )
            }
            else {

                this.properties['LOGGED_IN']=false;
                this.properties['logged_in']=false;
                this.topMainMenuService.showOnlyMenuItems([
                    'HELP','GLOSSARY'
                ])
            }
        })

    }

    async init(){

    }
}
