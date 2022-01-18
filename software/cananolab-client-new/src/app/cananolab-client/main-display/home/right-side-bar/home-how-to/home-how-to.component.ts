import { Component, OnInit } from '@angular/core';
import { UtilService } from '../../../../common/services/util.service';

@Component({
  selector: 'canano-home-how-to',
  templateUrl: './home-how-to.component.html',
  styleUrls: ['./home-how-to.component.scss']
})

// @TODO The links in the HTML don't work
export class HomeHowToComponent implements OnInit {

  constructor(private utilService: UtilService) { }

  ngOnInit(): void {
  }
    // @TODO this is dupe code
    openWindow( pageURL, name, width = 700, height  = 900) {
        window.open( pageURL, name, 'alwaysRaised,dependent,toolbar,status,scrollbars,resizable,width=' + width + ',height=' + height );
    }

}
