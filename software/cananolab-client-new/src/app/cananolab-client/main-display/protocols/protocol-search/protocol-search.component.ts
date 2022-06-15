import { Component, OnDestroy, OnInit } from '@angular/core';
import { Consts, ProtocolScreen } from '../../../../constants';
import { TopMainMenuService } from '../../../top-main-menu/top-main-menu.service';
import { ApiService } from '../../../common/services/api.service';
import { Properties } from '../../../../../assets/properties';
import { UtilService } from '../../../common/services/util.service';
import { ProtocolsService } from '../protocols.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
@Component( {
    selector: 'canano-protocol-search',
    templateUrl: './protocol-search.component.html',
    styleUrls: ['./protocol-search.component.scss']
} )
export class ProtocolSearchComponent implements OnInit, OnDestroy{
    protocolSearchForm;
    /**
     * For canano-main-display-heading @Input()
     */
    loading;
    loadingMessage=Consts.searchingMessage;
    helpUrl = Consts.HELP_URL_PROTOCOL_SEARCH;
    helpUrlSearchResults = Consts.HELP_URL_PROTOCOL_SEARCH_RESULTS;
    toolHeadingName = 'Protocol Search';
    toolHeadingNameSearchResults = 'Protocol Search Results';
    errors;
    // List for the dropdown
    protocolTypes = [];

    protocol_search_protocol_name = '';
    protocolSearchProtocolType = '';
    protocolSearchProtocolType1 = '';


    protocolScreenToShow = ProtocolScreen.PROTOCOL_SEARCH_INPUT_SCREEN;
    protocolScreenInfo = '';
    searchResults = [];

    defaultOperand = 'contains';
    nameOperand = '';
    titleOperand = '';
    abbreviationOperand = '';

    protocolType = '';

    resetting = false;
    initData;
    protocolScreen = ProtocolScreen;
    protocolName = '';

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private router:Router,private topMainMenuService: TopMainMenuService, private apiService: ApiService,
                 private utilService: UtilService, private protocolsService: ProtocolsService ){
    }

    ngOnInit(): void{
        this.errors={};
        console.log('test')
        this.protocolSearchForm={
            "nameOperand":this.defaultOperand,
            "titleOperand":this.defaultOperand,
            "abbreviationOperand":this.defaultOperand,
            "fileTitle":"",
            "protocolType":"",
            "protocolName":""
        };
        this.initData=JSON.parse(JSON.stringify(this.protocolSearchForm))

        // Listen for changing Protocol screens
        this.protocolsService.currentProtocolScreenEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            ( data ) => {
                this.errors={};
                this.protocolScreenToShow = data.ps;
                this.protocolScreenInfo = data.info;
            },
            error=> {
                this.errors=error;
            } );


        // Get Protocol types from server
        this.init();

    }


    onSubmit(  ){
        this.loading=true;
        this.loadingMessage=Consts.searchingMessage;
        this.apiService.doPost( Consts.QUERY_SEARCH_PROTOCOL, this.protocolSearchForm ).subscribe(
            data => {
                this.protocolsService.setProtocolSearchResults(data);
                this.router.navigate(['home/protocols/protocol-search-results']);
                this.errors={};
                this.loading=null;
            },
            err => {
                if( err.status === 404 ){ // @checkme
                    this.errors=err;
                    this.loading=null;
                }
            }
        );
    }


    init(){
        // Get list of Protocol types for dropdown
        if( Properties.PROTOCOL_TYPES.length < 1){
            this.loading=true;
            this.loadingMessage=Consts.loadingMessage;
            this.apiService.doGet( Consts.QUERY_PROTOCOL_SETUP, '' ).subscribe(
                data => {
                    this.errors={};
                    this.protocolTypes = <any>data['protocolTypes'];
                    Properties.PROTOCOL_TYPES = this.protocolTypes; // Cache it
                    this.loading=false;
                },
                error=> {
                    this.loading=null;
                    this.errors=error;
                } );
        }else{
            this.protocolTypes = Properties.PROTOCOL_TYPES;
        }
    }

    onResetClick(){
        this.resetting = true;
        this.protocolSearchForm=JSON.parse(JSON.stringify(this.initData));
    }


    ngOnDestroy(): void {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

}
