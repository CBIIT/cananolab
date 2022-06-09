import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { IdleService } from './idle.service';
import { Consts } from 'src/app/constants';
import { Properties } from 'src/assets/properties';
import { StatusDisplayService } from 'src/app/cananolab-client/status-display/status-display.service';
import { TopMainMenuService } from 'src/app/cananolab-client/top-main-menu/top-main-menu.service';
import { UtilService } from '../../services/util.service';
@Component({
  selector: 'canano-idle',
  templateUrl: './idle.component.html',
  styleUrls: ['./idle.component.scss']
})
export class IdleComponent implements OnInit {
    activeTimer;
    poppedUp=false;
    timeoutTime;
    constructor(
        private topMainMenuService:TopMainMenuService,
        private statusDisplayService:StatusDisplayService,
        private idleService:IdleService,
        private router:Router,
        private apiService:ApiService,
        private utilService:UtilService) { }
    ngOnInit(): void {
        this.timeoutTime=this.idleService.getTimeoutMessageTime();
        this.idleService.activeTimer.subscribe(data=> {
            this.activeTimer = data;
            if (this.activeTimer<this.timeoutTime) {
                if (this.poppedUp==false) {
                    document.getElementById('timeoutModalButton').click();
                    this.poppedUp=true;
                }
            }
            if (this.activeTimer==0) {
                this.logout();
                Properties['LOGGED_IN']=false;
                Properties['logged_in']=false;
                this.topMainMenuService.showOnlyMenuItems([
                    'HOME','HELP','GLOSSARY','PROTOCOLS','SAMPLES','PUBLICATIONS','LOGIN'
                ])
                this.router.navigate( [this.utilService.getRouteByName( 'HOME' )] );

                this.idleService.stopTimer();
            }
        })
    }

    closeModal() {
        this.idleService.resetTimer();
        this.poppedUp=false;
    }

    convertSecondsToMinutesSeconds() {
        let minutes=Math.floor(this.activeTimer/60);
        let seconds = this.activeTimer - minutes * 60;
        let timeLeft='';
        timeLeft=minutes.toString()+':';
        if (minutes==0) timeLeft = ':'
        if (seconds<10) {
            timeLeft+='0'+seconds.toString()
        }
        else {
            timeLeft+=seconds.toString()
        }
        return timeLeft;
    }

    logout(){
        this.poppedUp=false;
        document.getElementById('timeoutModalButtonClose').click();
        this.apiService.doPost( Consts.QUERY_LOGOUT, '' ).subscribe(
            data => {
                Properties.LOGGED_IN = false;
                this.statusDisplayService.updateUser( 'guest' );
            }
        );
    }

}
