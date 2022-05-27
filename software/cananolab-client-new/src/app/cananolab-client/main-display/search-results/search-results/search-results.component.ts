import { Component, OnInit } from '@angular/core';
import { connectableObservableDescriptor } from 'rxjs/internal/observable/ConnectableObservable';
import { TopKeywordSearchService } from 'src/app/cananolab-client/header/top-keyword-search/top-keyword-search.service';
import { Properties } from 'src/assets/properties';
import { Consts } from 'src/app/constants';
import { SortState } from 'src/app/constants';
import { Subject } from 'rxjs';
import { SearchResultsPagerService } from '../../../common/components/search-results-pager/search-results-pager.service';
import { takeUntil,timeout } from 'rxjs/operators';
import { StatusDisplayService } from 'src/app/cananolab-client/status-display/status-display.service';
import { Router } from '@angular/router';

@Component({
  selector: 'canano-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss']
})
export class SearchResultsComponent implements OnInit {
    searchResults;
    maxPageLength = Properties.MAX_PAGE_LENGTH;
    pageLength = Properties.DEFAULT_PAGE_LENGTH;
    columnHeadings = ['Actions', 'Type', 'Name', 'Created Date', 'Description'];

    helpUrl = Consts.HELP_URL_SAMPLE_SEARCH;
    toolHeadingNameSearchSample = 'Sample Search Results';
    pageCount = 10;
    searchResultsCount = -1;
    currentPage = 0;
    searchResultsPageToDisplay;

    sortingStates = [SortState.NO_SORT, SortState.SORT_UP, SortState.NO_SORT, SortState.NO_SORT, SortState.NO_SORT, SortState.NO_SORT, SortState.NO_SORT];

    sortState = SortState;
    properties = Properties;
    userName;

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();
    constructor(private router:Router,private statusDisplayService:StatusDisplayService,
                private searchResultsPagerService:SearchResultsPagerService,
                private topKeywordSearchService:TopKeywordSearchService) { }

    ngOnInit(): void {
        this.searchResults=this.topKeywordSearchService.getKeywordResults();
        this.searchResultsCount = this.searchResults.length;

        this.searchResultsPagerService.currentPageChangeEmitter.pipe( takeUntil( this.ngUnsubscribe ) )
            .subscribe( ( data ) => {
                this.currentPage = data;
                this.setupPage();
            } );

            console.log(this.properties)
        this.statusDisplayService.updateUserEmitter.pipe( timeout( Properties.HTTP_TIMEOUT ) ).subscribe(
            data => {
                console.log('is nothing happening?')
                this.userName = data;
            },
            error=>{
                console.log('error')
            } );


        this.searchResultsCount = this.searchResults.length;
        this.pageCount = Math.ceil( this.searchResultsCount / this.pageLength );
        this.onPageLengthChange();
    }

    navigateToResult( resultId,type ){
        console.log(type)
        if (type=='sample') {
            this.router.navigate(['/home/samples/sample',resultId]);

        }
        if (type=='publication'){
            this.router.navigate(['/home/samples/publications/publication',resultId]);
        }
        if (type=='protocol') {
            this.router.navigate(['/home/samples/protocols/protocol',resultId]);


        }
    }

    addToFavorites(result) {
        // let data = {
        //     "dataId":publication.id,
        //     "dataName":publication.displayName,
        //     "dataType":"publication",
        //     "editable":publication.editable,
        //     "loginName":this.userName,
        //     "pubMedId":publication.pubMedId
        // }
        // console.log(data)
        // this.apiService.doPost('caNanoLab/rest/core/addFavorite',)
    }

    onPageLengthChange(){
        if( this.pageLength < 1 ){
            this.pageLength = 1;
        }
        if( this.pageLength > this.maxPageLength ){
            this.pageLength = this.maxPageLength;
        }

        this.pageCount = Math.ceil( this.searchResultsCount / this.pageLength );
        this.searchResultsPagerService.setPageCount( this.pageCount );
        this.setupPage(); // Sets this page as the currently vied search results.
    }

    onSortClick( i ){
        console.log( 'onSortClick: ', i );
    }

    setupPage(){
        this.searchResultsPageToDisplay = this.searchResults.slice( this.pageLength * this.currentPage, this.pageLength * (this.currentPage + 1) );

    }


}
