// -------------------------------------------------------------------------
// ---------------------  "Navigation Tree" top left  ----------------------
// ---------------  (General Info, Composition, Publication)  --------------
// ---------------------  @TODO This will be dynamic  ----------------------
// -------------------------------------------------------------------------

import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'canano-left-navigation-menu',
  templateUrl: './left-navigation-menu.component.html',
  styleUrls: ['./left-navigation-menu.component.scss']
})
export class LeftNavigationMenuComponent implements OnInit {
    topHeading = 'Navigation Tree';

  constructor() { }

  ngOnInit(): void {
  }

}
