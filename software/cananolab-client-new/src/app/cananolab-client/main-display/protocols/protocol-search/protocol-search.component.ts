import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Consts, ProtocolScreen } from '../../../../constants';
import { TopMainMenuService } from '../../../top-main-menu/top-main-menu.service';
import { ApiService } from '../../../common/services/api.service';
import { Properties } from '../../../../../assets/properties';
import { UtilService } from '../../../common/services/util.service';
import { ProtocolsService } from '../protocols.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component( {
    selector: 'canano-protocol-search',
    templateUrl: './protocol-search.component.html',
    styleUrls: ['./protocol-search.component.scss']
} )
export class ProtocolSearchComponent implements OnInit, OnDestroy{
    @ViewChild('protocolSearchForm') protocolSearchForm: NgForm;
    /**
     * For canano-main-display-heading @Input()
     */
    helpUrl = Consts.HELP_URL_PROTOCOL_SEARCH;
    helpUrlSearchResults = Consts.HELP_URL_PROTOCOL_SEARCH_RESULTS;
    toolHeadingName = 'Protocol Search';
    toolHeadingNameSearchResults = 'Protocol Search Results';

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

    protocolScreen = ProtocolScreen;
    protocolName = '';

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private topMainMenuService: TopMainMenuService, private apiService: ApiService,
                 private utilService: UtilService, private protocolsService: ProtocolsService ){
    }

    ngOnInit(): void{
        this.nameOperand = this.defaultOperand;
        this.titleOperand = this.defaultOperand;
        this.abbreviationOperand = this.defaultOperand;

        this.topMainMenuService.showOnlyMenuItems(
            [
                'HOME',
                'WORKFLOW',
                'PROTOCOLS',
                'SAMPLES',
                'PUBLICATIONS',
                'GROUPS',
                'CURATION',
                'MY_WORKSPACE',
                'MY_FAVORITES'
            ]
        );
        if( Properties.LOGGED_IN ){
            this.topMainMenuService.showMenuItem( 'LOGOUT' );
        }

        // Listen for changing Protocol screens
        this.protocolsService.currentProtocolScreenEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            ( data ) => {
                this.protocolScreenToShow = data.ps;
                this.protocolScreenInfo = data.info;
            } );


        // Get Protocol types from server
        this.init();

    }


    onSubmit( f: NgForm ){
        // Search Button
        f = f.value;
        let parameters = '';
        Object.keys( f )
            .forEach( key => {
                let temp = f[key];
                if( temp !== undefined && temp !== null){
                    if( temp.length > 0 ){
                        parameters += '&' + key + '=' + f[key];
                    }
                }
            } );

        // QUERY_SEARCH_PROTOCOL
        // Do the search
        this.apiService.doPost( Consts.QUERY_SEARCH_PROTOCOL, parameters.substr( 1 ) ).subscribe(
            data => {
                this.protocolScreenToShow = ProtocolScreen.PROTOCOL_SEARCH_RESULTS_SCREEN;
                this.searchResults = data;
            },
            err => {
                if( err.status === 404 ){ // @checkme
                    alert( 'No search results.' );
                }
            }
        );
    }


    init(){

        // Get list of Protocol types for dropdown
        if( Properties.PROTOCOL_TYPES.length < 1){
            this.apiService.doGet( Consts.QUERY_PROTOCOL_SETUP, '' ).subscribe(
                data => {
                    this.protocolTypes = <any>data['protocolTypes'];

                    // Put an empty entry at the top of the Types dropdown.
                    this.protocolTypes.unshift('');

                    Properties.PROTOCOL_TYPES = this.protocolTypes; // Cache it
                } );
        }else{
            this.protocolTypes = Properties.PROTOCOL_TYPES;
        }
    }

    onResetClick(){
        this.resetting = true;
        this.protocolSearchForm.reset();

        this.nameOperand = this.defaultOperand;
        this.titleOperand = this.defaultOperand;
        this.abbreviationOperand = this.defaultOperand;
        this.protocolType = '';
    }


    ngOnDestroy(): void {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

}
