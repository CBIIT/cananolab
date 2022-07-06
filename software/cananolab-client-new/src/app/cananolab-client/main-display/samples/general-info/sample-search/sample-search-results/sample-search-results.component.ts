import { Component, OnDestroy, OnInit } from '@angular/core';
import { SampleSearchResultsService } from './sample-search-results.service';
import { Consts, ProtocolScreen, SortState } from '../../../../../../constants';
import { Properties } from '../../../../../../../assets/properties';
import { takeUntil, timeout } from 'rxjs/operators';
import { SearchResultsPagerService } from '../../../../../common/components/search-results-pager/search-results-pager.service';
import { Subject } from 'rxjs';
import { StatusDisplayService } from '../../../../../status-display/status-display.service';
import { ApiService } from '../../../../../common/services/api.service';
import { SampleAvailabilityDisplayService } from './sample-availability-display/sample-availability-display.service';
import { Router } from '@angular/router';

@Component({
    selector: 'canano-sample-search-results',
    templateUrl: './sample-search-results.component.html',
    styleUrls: ['./sample-search-results.component.scss'],
})
export class SampleSearchResultsComponent implements OnInit, OnDestroy {
    columnHeadings = [
        { actions: 'Actions' },
        { sampleName: 'Sample Name' },
        { pointOfContact: 'Primary Point of Contact' },
        { composition: 'Composition' },
        { functions: 'Functions' },
        { characterizations: 'Characterizations' },
        { dataAvailability: 'Data Availability' },
        { createdDate: 'Created' }
    ];

    maxPageLength = Properties.MAX_PAGE_LENGTH;
    pageLength = Properties.DEFAULT_PAGE_LENGTH;
    loading;
    searchResults;
    sampleIds=[];
    helpUrl = Consts.HELP_URL_SAMPLE_SEARCH;
    toolHeadingNameSearchSample = 'Sample Search Results';
    pageCount = 10;
    searchResultsCount = -1;
    currentPage = 0;
    searchResultsPageToDisplay;

    sortingStates = [
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT
    ];

    sortState = SortState;
    properties = Properties;
    userName;

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor(
        private sampleSearchResultsService: SampleSearchResultsService,
        private searchResultsPagerService: SearchResultsPagerService,
        private statusDisplayService: StatusDisplayService,
        private apiService: ApiService,
        private router: Router,
        private sampleAvailabilityDisplayService: SampleAvailabilityDisplayService
    ) {}

    ngOnInit(): void {
        setTimeout(()=> {
            Properties.SAMPLE_TOOLS = false;
        })
        this.searchResults = this.sampleSearchResultsService.getSearchResults();
        this.searchResultsCount = this.searchResults.length;
        this.sampleIds=this.sampleSearchResultsService.getSampleIds();
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

    navigateToSampleEdit(sampleId) {
        console.log('test');
        this.router.navigate(['home/samples/sample', sampleId]); // @FIXME  Don't hard code these
    }

    navigateToSampleView(sampleId, sampleName) {
        this.router.navigate(['home/samples/view-sample', sampleId]); // @FIXME  Don't hard code these
    }

    addToFavorites(samp) {
        console.log('addToFavorites samp: ', samp);
        let favObj = { dataType: 'sample', loginName: this.userName}; // @FIXME User real user name
        favObj['dataName'] = samp['sampleName'];
        favObj['dataId'] = samp['sampleId'];
        favObj['description'] = samp['nanoEntityDesc'];
        favObj['editable'] = samp['editable'];
        favObj['loginName']=Properties.current_user;

        console.log('favObj:', favObj);

        this.apiService.doPost(Consts.QUERY_ADD_FAVORITE, favObj).subscribe(
            (data) => {
                samp['addedToFavorites']=data;
                console.log(samp)
                // console.log('set Fave results: ', data);
            },
            (err) => {
                console.log('ERROR QUERY_ADD_FAVORITE: ', err);
            }
        );
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


    downloadReady(event) {
        if (event==true) {
            this.loading=null;
        }
        if (event==false) {
            this.loading=true;
        }
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
        if (i) {
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
}
