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
import { Router,Event,NavigationStart,NavigationEnd } from '@angular/router';
import { UtilService } from './common/services/util.service';
import { ApiService } from './common/services/api.service';
import { TopMainMenuService } from './top-main-menu/top-main-menu.service';
import { StatusDisplayService } from './status-display/status-display.service';
import { Consts } from '../constants';
import { IdleService } from './common/components/idle/idle.service';
@Component( {
    selector: 'canano-cananolab-client',
    templateUrl: './cananolab-client.component.html',
    styleUrls: ['./cananolab-client.component.scss']
} )
export class CananolabClientComponent implements OnInit{

    properties = Properties;
    currentRoute;
    menuItems=[];
    constructor( private idleService:IdleService,private statusDisplayService:StatusDisplayService,private topMainMenuService:TopMainMenuService,private apiService:ApiService,private configurationService: ConfigurationService, private router: Router,
                 private utilService: UtilService ){

    }

    ngOnInit(): void{
        this.currentRoute = "";
        this.router.events.subscribe((event: Event) => {
            if (event instanceof NavigationStart) {
                if (!event.url.includes('home/samples')) { // prevents menu refresh pause //
                    Properties.SAMPLE_TOOLS=false;
                }
                if (event.url!='/home') {
                    this.menuItems=['HOME'];
                }
            }
            if (event instanceof NavigationEnd) {
                this.idleService.startTimer();
            }
        })
        let loginUrl=this.apiService.doGet(Consts.QUERY_GET_USER_GROUPS,{});
        loginUrl.subscribe(data=> {
            let keys=Object.keys(data);
            if (keys[0]!='anonymousUser') {
                this.properties['LOGGED_IN']=true;
                this.properties['logged_in']=true;
                this.properties['current_user']=keys[0];
                this.statusDisplayService.updateUser(this.properties['current_user']);
                let tabs=[];
                this.apiService.getTabs().subscribe(data=> {
                        data['tabs'].forEach(element => {
                            this.menuItems.push(element[0].replace(' ','_'))
                            if (element[0]=='CURATION') {
                                this.menuItems.push('RESULTS');
                            }
                        });
                        this.topMainMenuService.showOnlyMenuItems(
                            this.menuItems
                        )
                })
                // this.menuItems.push('HOME','WORKFLOW','PROTOCOLS','SAMPLES','PUBLICATIONS','GROUPS','CURATION','MY_WORKSPACE','MY_FAVORITES','LOGOUT');
            }
            else {

                this.properties['LOGGED_IN']=false;
                this.properties['logged_in']=false;
                this.menuItems.push('HOME','HELP','GLOSSARY','PROTOCOLS','SAMPLES','PUBLICATIONS','LOGIN');
                this.topMainMenuService.showOnlyMenuItems(this.menuItems)
            }
        })
    }


    navigateToUrl(url) {
        window.open(url);
    }

    async init(){

    }
}
