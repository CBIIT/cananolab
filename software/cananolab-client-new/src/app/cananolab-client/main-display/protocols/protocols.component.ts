import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../constants';
import { Properties } from 'src/assets/properties';
@Component({
  selector: 'canano-protocols',
  templateUrl: './protocols.component.html',
  styleUrls: ['./protocols.component.scss']
})
export class ProtocolsComponent implements OnInit {

    toolHeadingName = 'Manage Protocols';
    helpUrl = Consts.HELP_URL_PROTOCOL_MANAGE;
    properties=Properties;
  constructor( ) { }

  ngOnInit(): void {
    console.log(Properties)
  }


}
