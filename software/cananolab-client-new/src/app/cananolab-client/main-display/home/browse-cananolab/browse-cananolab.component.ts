import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../common/services/api.service';
import { Consts } from '../../../../constants';
import { MainDisplayService } from '../../main-display.service';
import { TopMenuItems } from '../../../top-main-menu/top-main-menu.service';
import { UtilService } from '../../../common/services/util.service';
import { Router } from '@angular/router';

@Component( {
    selector: 'canano-browse-cananolab',
    templateUrl: './browse-cananolab.component.html',
    styleUrls: ['./browse-cananolab.component.scss']
} )
export class BrowseCananolabComponent implements OnInit{
    initData = {};

    constructor( private apiService: ApiService, private mainDisplayService: MainDisplayService,
                 private router: Router, private utilService: UtilService ){
    }

    ngOnInit(): void{
        this.init();

    }

    async init(){

        this.apiService.doGet( Consts.QUERY_INIT_SETUP, '' ).subscribe(
            data => {
                this.initData = data;
                console.log( 'MHL QUERY_INIT_SETUP: ', data );
            } );

    }

    onSearchProtocolsClick(){
        console.log( 'MHL BrowseCananolabComponent: onSearchProtocolsClick()' );
        this.router.navigate( [this.utilService.getRouteByName( 'PROTOCOLS' )] );
    }

    // Will not need this after router is in place?
    onSearchPublicationsClick(){
        console.log( 'MHL BrowseCananolabComponent: onSearchPublicationsClick()' );
        this.router.navigate( [this.utilService.getRouteByName( 'PUBLICATIONS' )] );

    }

    onSearchSamplesClick(){
        this.router.navigate( [this.utilService.getRouteByName( 'SAMPLES' )] );

    }
}
