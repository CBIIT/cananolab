import { Component, OnInit } from '@angular/core';
import { StatusDisplayService } from './status-display.service';
import { timeout } from 'rxjs/operators';
import { Properties } from '../../../assets/properties';
import { ChildActivationStart } from '@angular/router';

@Component({
  selector: 'canano-status-display',
  templateUrl: './status-display.component.html',
  styleUrls: ['./status-display.component.scss']
})
export class StatusDisplayComponent implements OnInit {
  userName = 'TEST User';  // @TODO
  constructor(private statusDisplayService: StatusDisplayService) { }

  ngOnInit(): void {
      this.statusDisplayService.updateUserEmitter.pipe( timeout( Properties.HTTP_TIMEOUT ) ).subscribe(
          data => {
              this.userName = data;
          });

  }

}
