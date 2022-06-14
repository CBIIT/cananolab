import { Component, OnInit } from '@angular/core';
import { Properties } from '../../../../../../assets/properties';

@Component({
  selector: 'canano-home-user-actions',
  templateUrl: './home-user-actions.component.html',
  styleUrls: ['./home-user-actions.component.scss']
})
export class HomeUserActionsComponent implements OnInit {
    properties = Properties;

  constructor() { }

  ngOnInit(): void {
      console.log(this.properties)
  }

}
