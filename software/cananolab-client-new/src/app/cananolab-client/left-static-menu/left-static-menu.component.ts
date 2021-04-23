import { Component, OnInit } from '@angular/core';
import { UtilService } from '../common/services/util.service';

@Component( {
    selector: 'canano-left-static-menu',
    templateUrl: './left-static-menu.component.html',
    styleUrls: ['./left-static-menu.component.scss']
} )
export class LeftStaticMenuComponent implements OnInit{

    newWindowHeight = 800;
    newWindowWidth = 800;
    topHeading = 'Related Links';
    utilService;

    constructor(private utilServiceX: UtilService) {
    }

    ngOnInit(): void {
      //  this.utilService = this.utilServiceX;
    }

    // @TODO this is dup code
    openWindow( pageURL, name, width = this.newWindowWidth, height  = this.newWindowHeight) {
        window.open( pageURL, name, 'alwaysRaised,dependent,toolbar,status,scrollbars,resizable,width=' + width + ',height=' + height );
    }

}
