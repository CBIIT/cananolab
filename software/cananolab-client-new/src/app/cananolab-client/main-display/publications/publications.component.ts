import { Component, OnInit } from '@angular/core';
import { MainDisplayService } from '../main-display.service';
import { Consts } from '../../../constants';

@Component({
  selector: 'canano-publications',
  templateUrl: './publications.component.html',
  styleUrls: ['./publications.component.scss']
})
export class PublicationsComponent implements OnInit {
    /**
     * These will be provided to canano-main-display-heading as @Input
     */
    helpUrl = Consts.HELP_URL_PUBLICATIONS;
    toolHeadingName = 'Manage Publications';

  constructor( private mainDisplayService: MainDisplayService) { }

  ngOnInit(): void {
  }

}
