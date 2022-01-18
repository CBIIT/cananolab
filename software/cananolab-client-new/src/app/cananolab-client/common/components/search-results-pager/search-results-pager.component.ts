import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Properties } from '../../../../../assets/properties';
import { Subject } from 'rxjs';
import { SearchResultsPagerService } from './search-results-pager.service';
import { takeUntil } from 'rxjs/operators';
import { UtilService } from '../../services/util.service';

@Component( {
    selector: 'canano-search-results-pager',
    templateUrl: './search-results-pager.component.html',
    styleUrls: ['./search-results-pager.component.scss']
} )
export class SearchResultsPagerComponent implements OnInit, OnDestroy{

    /**
     * Total number of results (not pages).
     */
    @Input() totalCount;
    @Input() startingPageLength;

    debugId = Properties.COUNTER++;

    currentPage = 0;

    /**
     * How many "Number" buttons to show if there are more than Properties.MAX_PAGER_BUTTONS pages.
     * @type {number}
     */
    maxButtonsToShow = Properties.MAX_PAGER_BUTTONS;

    /**
     * If there are more than Properties.MAX_PAGER_BUTTONS pages, the visible number buttons will scroll.<br>
     * This is the first (left most) visible button.  Zero is the first page.
     * @type {number}
     */
    buttonShowOffset = 0;

    /**
     * @TODO explain
     * @type {number}
     */
    direction = 0;


    /**
     * An array representing each button, this currently just holds the text for the button (which is just the index plus one).
     * @type {Array}
     */
    buttons = [];

    pageCount = 0;

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private searchResultsPagerService: SearchResultsPagerService,
                 private utilService: UtilService ){
        console.log( 'MHL 400A constructor SearchResultsPagerComponent @Input to here totalCount: ', this.totalCount );
    }

    ngOnInit(){
        this.searchResultsPagerService.pageCountEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe( ( data ) => {
            console.log( 'MHL 400b searchResultsPagerService.pageCountEmitter data: ', data );
            this.pageCount = data;
            console.log( 'MHL ngOnInit 402 SearchResultsPagerComponent @Input to here totalCount: ', this.totalCount );
            this.setButtons();
            console.log( 'MHL ngOnInit 403 SearchResultsPagerComponent @Input to here totalCount: ', this.totalCount );
        } );

        // This is for syncing up to pagers
        this.searchResultsPagerService.currentPageChangeEmitter.pipe( takeUntil( this.ngUnsubscribe ) )
            .subscribe( ( data ) => {
                this.currentPage = data;
                console.log( 'MHL [' + this.debugId + ']currentPage: ', this.currentPage );
                console.log( 'MHL [' + this.debugId + ']pageCount: ', this.pageCount );
                if( this.currentPage === 0 ){
                    this.buttonShowOffset = 0;
                }else if( this.currentPage > (this.buttonShowOffset + this.maxButtonsToShow - 1) ){
                    this.buttonShowOffset++;
                }else if( this.currentPage < this.buttonShowOffset ){
                    this.buttonShowOffset--;
                }else if( this.currentPage === (this.pageCount - 1) ){
                    console.log( 'MHL [' + this.debugId + '] pageCount: ', this.pageCount + ' Going to the end' );
                    console.log( 'MHL [' + this.debugId + '] maxButtonsToShow: ', this.maxButtonsToShow + ' Going to the end' );
                    this.buttonShowOffset = this.pageCount - this.maxButtonsToShow;
                    console.log( 'MHL [' + this.debugId + '] buttonShowOffset: ', this.buttonShowOffset + ' Going to the end' );
                }
                console.log( 'MHL [' + this.debugId + '] buttonShowOffset: ', this.buttonShowOffset );

            } );


        this.searchResultsPagerService.nextPageEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe( () => {
            this.onGoNextClick();
        } );

        this.pageCount = Math.ceil( this.totalCount / this.startingPageLength );
        this.setButtons();
    }


    /**
     * Initialize the buttons with their correct numbers - called when row count, or rows per page changes.
     */
    setButtons(){
        console.log( 'MHL 888 ----------------------------------------- setButtons  pageCount: ', this.pageCount );
        this.buttons = [];
        for( let f = 0; f < this.pageCount; f++ ){
            this.buttons[f] = f;
        }
        if( this.pageCount < this.currentPage ){
            this.onGoLastClick();
        }
    }


    onGoFirstClickClick(){
        this.currentPage = 0;
        this.buttonShowOffset = 0;
        // this.searchResultsPagerService.currentPageChangeEmitter.emit( this.currentPage );
        this.searchResultsPagerService.setCurrentPage( this.currentPage );
    }

    onGoPreviousClick(){
        this.currentPage--;
        if( this.currentPage < 0 ){
            this.currentPage = 0;
        }else{
            // this.searchResultsPagerService.currentPageChangeEmitter.emit( this.currentPage );
            this.searchResultsPagerService.setCurrentPage( this.currentPage );
        }

        if( this.currentPage < this.buttonShowOffset ){
            this.buttonShowOffset--;
        }

    }

    onGoNextClick(){
        if( this.pageCount === (this.currentPage + 1) ){
            return;
        }

        this.currentPage++;
        // this.searchResultsPagerService.currentPageChangeEmitter.emit( this.currentPage );
        this.searchResultsPagerService.setCurrentPage( this.currentPage );

        if( this.currentPage > (this.buttonShowOffset + this.maxButtonsToShow - 1) ){
            this.buttonShowOffset++;
        }

    }

    onGoLastClick(){
        this.currentPage = this.pageCount - 1;
        this.buttonShowOffset = this.pageCount - this.maxButtonsToShow;
        // this.searchResultsPagerService.currentPageChangeEmitter.emit( this.currentPage );
        this.searchResultsPagerService.setCurrentPage( this.currentPage );
    }

    onClick( i ){
        this.currentPage = i;
       // this.searchResultsPagerService.currentPageChangeEmitter.emit( this.currentPage );
        this.searchResultsPagerService.setCurrentPage( this.currentPage );
    }


    ngOnDestroy(){
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }
}
