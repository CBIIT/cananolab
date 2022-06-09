import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ApiService } from '../../services/api.service';
import { Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Consts } from 'src/app/constants';
@Injectable({
  providedIn: 'root'
})
export class IdleService {
    @Output() activeTimer: EventEmitter<any> = new EventEmitter<any>();
    modal;
    timeoutMessageTime=Consts.timeoutWarning;
    timerSeconds = Consts.sessionTimeInSeconds;
    theTimer;
    constructor(private router:Router,private apiService:ApiService) { }

    getTimeoutMessageTime() {
        return this.timeoutMessageTime;
    }

    logout() {
        this.activeTimer.emit(69)
    }

    timer = new Observable((observer) => {
        let timer=this.timerSeconds;
        setInterval(()=> {
            observer.next(timer-=1);
        },1000)

        return {
          unsubscribe() {
            timer=this.timerSeconds;
          }
        };
      });

    resetTimer() {
        this.stopTimer();
        this.startTimer();
    }


    stopTimer() {
        if (this.theTimer) {
            this.theTimer.unsubscribe();
        }
    }

    startTimer() {
        setTimeout(()=> {
            this.apiService.doGet(Consts.QUERY_GET_USER_GROUPS,'').subscribe(data=> {
                if (Object.keys(data).indexOf('anonymousUser')==-1) {
                    if (this.theTimer) {
                        this.theTimer.unsubscribe();
                    }
                    this.theTimer = this.timer.subscribe(data=> {
                        this.activeTimer.emit(data);
                    });

                }
                else {
                    if (this.theTimer) {
                        this.theTimer.unsubscribe();
                    };
                }
            })
        },100)
    }
}
