import { Component, Input, OnInit } from '@angular/core';
import * as clone from 'clone';

@Component({
  selector: 'canano-manage-accessibility',
  templateUrl: './manage-accessibility.component.html',
  styleUrls: ['./manage-accessibility.component.scss']
})
export class ManageAccessibilityComponent implements OnInit {

    @Input() accessData = 'Place Holder_03';
    temp = 'Place Holder_01';
  constructor() {

  }

  ngOnInit(): void {
       if( this.accessData !== undefined){
           this.temp = clone( this.accessData);
           console.log('MHL clone: ', this.temp);
       }else{
           console.log('MHL accessData clone SKIPPED this.accessData: ', this.accessData);
       }

      /*

            if( this.accessData !== undefined){

                this.temp = clone( this.accessData) ;
                console.log( 'MHL ManageAccessibilityComponent data: ', this.accessData );
                console.log( 'MHL ManageAccessibilityComponent temp: ', this.temp);
            }
      */

      if( this.accessData !== undefined && this.accessData['recipientDisplayName'] !== undefined){
                 // this.temp = clone( this.accessData) ;
          console.log( 'MHL ManageAccessibilityComponent data: ', this.accessData['recipientDisplayName'] );
          console.log( 'MHL ManageAccessibilityComponent temp: ', this.temp['recipientDisplayName']);
      }
  }
but(){
    console.log( 'MHL 21 ManageAccessibilityComponent temp: ', this.temp);

}
}
