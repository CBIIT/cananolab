import { Component, ElementRef, OnInit } from '@angular/core';
import { Consts } from '../../../constants';
import { TopMainMenuService } from '../../top-main-menu/top-main-menu.service';
import { Properties } from '../../../../assets/properties';
import { ApiService } from '../../common/services/api.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'canano-protocols',
  templateUrl: './protocols.component.html',
  styleUrls: ['./protocols.component.scss']
})
export class ProtocolsComponent implements OnInit {

    toolHeadingName = 'Manage Protocols';
    helpUrl = Consts.HELP_URL_PROTOCOL_MANAGE;

  constructor( ) { }

  ngOnInit(): void {

  }


}
