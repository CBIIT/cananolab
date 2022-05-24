import { Component, OnInit } from '@angular/core';
import { SearchPublicationService } from '../../search-publication/search-publication.service';
import { Properties } from '../../../../../../assets/properties';
import { SearchResultsPagerService } from '../../../../common/components/search-results-pager/search-results-pager.service';
import { takeUntil, timeout } from 'rxjs/operators';
import { Consts,SortState} from '../../../../../constants';
import { StatusDisplayService } from '../../../../status-display/status-display.service';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
@Component({
  selector: 'canano-searchpublicationresults',
  templateUrl: './searchpublicationresults.component.html',
  styleUrls: ['./searchpublicationresults.component.scss']
})
export class SearchpublicationresultsComponent implements OnInit {
    maxPageLength = Properties.MAX_PAGE_LENGTH;
    pageLength = Properties.DEFAULT_PAGE_LENGTH;
    columnHeadings = ['Actions', 'Bibliography Info', 'Publication Type', 'Research Category', 'Associated Sample Names', 'Description', 'Publication Status', 'Created'];

    searchResults;
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

    constructor(private router:Router,private statusDisplayService:StatusDisplayService,private searchResultsPagerService:SearchResultsPagerService,private searchPublicationService:SearchPublicationService) { }

    ngOnInit(): void {
        this.searchResults = this.searchPublicationService.getPublicationSearchResults();
        this.searchResultsCount = this.searchResults.length;

        this.searchResultsPagerService.currentPageChangeEmitter.pipe( takeUntil( this.ngUnsubscribe ) )
            .subscribe( ( data ) => {
                this.currentPage = data;
                this.setupPage();
            } );


        this.statusDisplayService.updateUserEmitter.pipe( timeout( Properties.HTTP_TIMEOUT ) ).subscribe(
            data => {
                this.userName = data;
            } );


        this.searchResultsCount = this.searchResults.length;
        this.pageCount = Math.ceil( this.searchResultsCount / this.pageLength );
        this.onPageLengthChange();
    }

    navigateToPublication( publicationId ){
        console.log(publicationId)
        this.router.navigate(['/home/samples/publications/publication',publicationId]);
    }

    // navigateToSampleView(publicationId){
    //     this.router.navigate(['home/samples/publications/publication', publicationId ]);  // @FIXME  Don't hard code these
    // }

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

    setupPage(){
        this.searchResultsPageToDisplay = this.searchResults.slice( this.pageLength * this.currentPage, this.pageLength * (this.currentPage + 1) );

    }


}
