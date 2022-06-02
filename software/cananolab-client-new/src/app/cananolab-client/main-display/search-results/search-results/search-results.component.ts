import { Component, OnInit } from '@angular/core';
import { connectableObservableDescriptor } from 'rxjs/internal/observable/ConnectableObservable';
import { TopKeywordSearchService } from '../../../header/top-keyword-search/top-keyword-search.service';
import { Properties } from '../../../../../assets/properties';
import { Consts } from '../../../../constants';
import { SortState } from '../../../../constants';
import { Subject } from 'rxjs';
import { SearchResultsPagerService } from '../../../common/components/search-results-pager/search-results-pager.service';
import { takeUntil,timeout } from 'rxjs/operators';
import { StatusDisplayService } from '../../../status-display/status-display.service';
import { Router } from '@angular/router';
import { ApiService } from '../../../common/services/api.service';
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
    errors={};
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
    constructor(private apiService:ApiService,private router:Router,private statusDisplayService:StatusDisplayService,
                private searchResultsPagerService:SearchResultsPagerService,
                private topKeywordSearchService:TopKeywordSearchService) { }

    ngOnInit(): void {
        this.searchResults=this.topKeywordSearchService.getKeywordResults();
        this.searchResultsCount = this.searchResults.length;

        this.searchResultsPagerService.currentPageChangeEmitter.pipe( takeUntil( this.ngUnsubscribe ) )
            .subscribe( ( data ) => {
                this.currentPage = data;
                this.setupPage();
                this.errors={};
            },
            errors=> {
                this.errors=errors;
            } );

            console.log(this.properties)
        this.statusDisplayService.updateUserEmitter.pipe( timeout( Properties.HTTP_TIMEOUT ) ).subscribe(
            data => {
                this.userName = data;
                this.errors={};
            },
            error=>{
                this.errors=error;
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
            this.router.navigate(['/home/protocols/edit-protocol',resultId]);


        }
    }

    addToFavorites(result) {
        let data = {
            "dataId":result.id,
            "dataName":result.name,
            "dataType":result.type,
            "editable":result.editable,
            "loginName":Properties.current_user
        }

        if (result.type=='protocol') {
            data['protocolFileId']=result.fileId;
            data['protocolFileTitle']=result.fileTitle;
        }
        this.apiService.doPost(Consts.QUERY_ADD_FAVORITE,data).subscribe(data=> {
            this.errors={};
        },
        error=> {
            this.errors=error;
        })
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
