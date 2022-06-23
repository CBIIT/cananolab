import { Component, OnInit,Input } from '@angular/core';

@Component({
  selector: 'canano-disclaimer',
  templateUrl: './disclaimer.component.html',
  styleUrls: ['./disclaimer.component.scss']
})
export class DisclaimerComponent implements OnInit {
    @Input() hide;
    constructor() { }

    ngOnInit(): void {

    }

}
