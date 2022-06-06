import { Component, OnInit,OnDestroy } from '@angular/core';
import { AdvancedSearchService } from '../advanced-search/advanced-search.service';
import { Properties } from 'src/assets/properties';
import { SortState,Consts } from 'src/app/constants';
import { SearchResultsPagerService } from 'src/app/cananolab-client/common/components/search-results-pager/search-results-pager.service';
import { StatusDisplayService } from 'src/app/cananolab-client/status-display/status-display.service';
import { SampleAvailabilityDisplayService } from '../sample-search-results/sample-availability-display/sample-availability-display.service';
import { takeUntil, timeout } from 'rxjs/operators';
import { ApiService } from 'src/app/cananolab-client/common/services/api.service';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
@Component({
  selector: 'canano-advanced-search-results',
  templateUrl: './advanced-search-results.component.html',
  styleUrls: ['./advanced-search-results.component.scss']
})
export class AdvancedSearchResultsComponent implements OnInit,OnDestroy {
    columnHeadings = [
    ];


    sortingStates = [
        SortState.NO_SORT
    ];
    data;
    searchResults;
    pageCount = 10;
    searchResultsCount = -1;
    currentPage = 0;
    searchResultsPageToDisplay;
    maxPageLength = Properties.MAX_PAGE_LENGTH;
    pageLength = Properties.DEFAULT_PAGE_LENGTH;
    sortState = SortState;
    properties = Properties;
    userName;
    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor(
        private advancedSearchService:AdvancedSearchService,
        private searchResultsPagerService: SearchResultsPagerService,
        private statusDisplayService: StatusDisplayService,
        private apiService: ApiService,
        private router: Router,
        private sampleAvailabilityDisplayService: SampleAvailabilityDisplayService
        ) { }



    ngOnInit(): void {
        this.data=this.advancedSearchService.getAdvancedSearchResults();
        this.searchResults=this.data['resultTable']['dataRows'];
        this.setupColumnHeadings();
        this.searchResultsCount = this.searchResults.length;

        this.searchResultsPagerService.currentPageChangeEmitter
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe((data) => {
                this.currentPage = data;
                this.setupPage();
            });

        this.statusDisplayService.updateUserEmitter
            .pipe(timeout(Properties.HTTP_TIMEOUT))
            .subscribe((data) => {
                this.userName = data;
            });

        this.searchResultsCount = this.searchResults.length;
        this.pageCount = Math.ceil(this.searchResultsCount / this.pageLength);
        this.onPageLengthChange();
    }
    navigateToSample(samp) {
        this.router.navigate(['home/samples/view-sample', samp.sampleId]);
    }

    onAvailabilityClick(id) {
        this.apiService
            .doGet(Consts.QUERY_SAMPLE_AVAILABILITY, 'sampleId=' + id)
            .subscribe(
                (data) => {
                    this.sampleAvailabilityDisplayService.displayStuff(data);
                },
                (err) => {
                    console.log('ERROR QUERY_SAMPLE_AVAILABILITY: ', err);
                }
            );
    }

    setupPage() {
        this.searchResultsPageToDisplay = this.searchResults.slice(
            this.pageLength * this.currentPage,
            this.pageLength * (this.currentPage + 1)
        );
    }

    onPageLengthChange() {
        if (this.pageLength < 1) {
            this.pageLength = 1;
        }
        if (this.pageLength > this.maxPageLength) {
            this.pageLength = this.maxPageLength;
        }

        this.pageCount = Math.ceil(this.searchResultsCount / this.pageLength);
        this.searchResultsPagerService.setPageCount(this.pageCount);
        this.setupPage(); // Sets this page as the currently vied search results.
    }

    onSortClick(i, key) {
        if (i>=0) {
            if (this.sortingStates[i]) {
                // clicking on column that already is sorted on //
                this.sortingStates[i] = this.sortingStates[i] == 1 ? 2 : 1;
            } else {
                // reset sorting states //
                this.sortingStates.forEach((item, index) => {
                    this.sortingStates[index] = 0;
                });
                this.sortingStates[i] = 1;
            }
            console.log(this.sortingStates)
            if (this.sortingStates[i] == 1) {
                this.searchResults.sort((a, b) => (this.getStringValue(a[key]) > this.getStringValue(b[key]) ? 1 : -1));
            } else {
                this.searchResults.sort((a, b) => (this.getStringValue(a[key]) < this.getStringValue(b[key]) ? 1 : -1));
            }
            this.setupPage();
        }
    }

    getStringValue(val) {
        if (val) {
            return val.toString().toUpperCase()
        }
        return ''
    }

    ngOnDestroy() {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

    setupColumnHeadings(){
        this.data['resultTable']['columnTitles'].forEach(item=> {
            let heading={};
            heading[item['value']]=item['label'];
            this.sortingStates.push(SortState.NO_SORT);
            this.columnHeadings.push(heading);
        })
    }

    splitValue(val) {
        return val.split('|')
    }
}
