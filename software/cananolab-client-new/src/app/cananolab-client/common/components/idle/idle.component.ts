import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'canano-idle',
  templateUrl: './idle.component.html',
  styleUrls: ['./idle.component.scss']
})
export class IdleComponent implements OnInit {
    modal;
    constructor() { }

    ngOnInit(): void {
        this.modal=document.getElementById('exampleModal');
    }



    timeoutShow() {
        document.getElementById('showTimeoutButton').click();
    }


    @HostListener('document:click', ['$event'])
    onasd(event: MouseEvent) {
        console.log(event)
    }

}
