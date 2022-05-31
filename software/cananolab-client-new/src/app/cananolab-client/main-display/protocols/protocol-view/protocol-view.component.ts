import { Component, OnInit } from '@angular/core';
import { ProtocolsService } from '../protocols.service';

@Component( {
    selector: 'canano-protocol-view',
    templateUrl: './protocol-view.component.html',
    styleUrls: ['./protocol-view.component.scss']
} )
export class ProtocolViewComponent implements OnInit{

    currentProtocol;

    constructor( private protocolsService: ProtocolsService ){
    }

    ngOnInit(): void{
       this.currentProtocol = this.protocolsService.getCurrentProtocolInfo();
    }

}
