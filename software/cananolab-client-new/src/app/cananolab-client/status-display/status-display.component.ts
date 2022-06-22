import { Component, OnInit } from '@angular/core';
import { StatusDisplayService } from './status-display.service';
import { timeout } from 'rxjs/operators';
import { Properties } from '../../../assets/properties';
import { TopMainMenuService } from '../top-main-menu/top-main-menu.service';
@Component({
  selector: 'canano-status-display',
  templateUrl: './status-display.component.html',
  styleUrls: ['./status-display.component.scss']
})
export class StatusDisplayComponent implements OnInit {
  userName;  // @TODO
  groups;
  properties=Properties;
  constructor(private topMainMenuService:TopMainMenuService,private statusDisplayService: StatusDisplayService) { }

  ngOnInit(): void {
    this.statusDisplayService.updateUserEmitter.pipe( timeout( Properties.HTTP_TIMEOUT ) ).subscribe(
    data => {
        this.userName = data;
    });
    this.statusDisplayService.updateGroupEmitter.subscribe(data=> {
        let keys=Object.keys(data);
        keys.forEach(key=> {
            this.groups=data[key].join('<br />')
        })
        // this.groups=data;
        // let
        // console.log(data)
    })
  }
}
