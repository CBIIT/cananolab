import { Component, OnInit } from '@angular/core';
import { TopKeywordSearchService } from '../../../header/top-keyword-search/top-keyword-search.service';
import { Properties } from '../../../../../assets/properties';
import { Consts } from '../../../../constants';
import { SortState } from '../../../../constants';
import { Subject } from 'rxjs';
import { SearchResultsPagerService } from '../../../common/components/search-results-pager/search-results-pager.service';
import { takeUntil, timeout } from 'rxjs/operators';
import { StatusDisplayService } from '../../../status-display/status-display.service';
import { Router } from '@angular/router';
import { ApiService } from '../../../common/services/api.service';
@Component({
    selector: 'canano-search-results',
    templateUrl: './search-results.component.html',
    styleUrls: ['./search-results.component.scss'],
})
export class SearchResultsComponent implements OnInit {
    columnHeadings = [
        { actions: 'Actions' },
        { type: 'Type' },
        { name: 'Name' },
        { createdDate: 'Created Date' },
        { description: 'Description' },
    ];
    currentPage = 0;
    errors = {};
    helpUrl = Consts.HELP_URL_SAMPLE_SEARCH;
    maxPageLength = Properties.MAX_PAGE_LENGTH;
    pageCount = 10;
    pageLength = Properties.DEFAULT_PAGE_LENGTH;
    properties = Properties;
    searchResults;
    searchResultsCount = -1;
    searchResultsPageToDisplay;
    sortingStates = [
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
    ];
    sortState = SortState;
    toolHeadingNameSearchSample = 'Sample Search Results';
    userName;

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();
    constructor(
        private apiService: ApiService,
        private router: Router,
        private statusDisplayService: StatusDisplayService,
        private searchResultsPagerService: SearchResultsPagerService,
        private topKeywordSearchService: TopKeywordSearchService
    ) {}

    ngOnInit(): void {
        this.searchResults = this.topKeywordSearchService.getKeywordResults();
        this.searchResultsCount = this.searchResults.length;
        this.searchResultsPagerService.currentPageChangeEmitter
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe(
                (data) => {
                    this.currentPage = data;
                    this.setupPage();
                    this.errors = {};
                },
                (errors) => {
                    this.errors = errors;
                }
            );
        this.statusDisplayService.updateUserEmitter
            .pipe(timeout(Properties.HTTP_TIMEOUT))
            .subscribe(
                (data) => {
                    this.userName = data;
                    this.errors = {};
                },
                (error) => {
                    this.errors = error;
                    console.log('error');
                }
            );
        this.searchResultsCount = this.searchResults.length;
        this.pageCount = Math.ceil(this.searchResultsCount / this.pageLength);
        this.onPageLengthChange();
    }

    addToFavorites(result) {
        let data = {
            dataId: result.id,
            dataName: result.name,
            dataType: result.type,
            editable: result.editable,
            loginName: Properties.current_user,
        };

        if (result.type == 'protocol') {
            data['protocolFileId'] = result.fileId;
            data['protocolFileTitle'] = result.fileTitle;
        }
        this.apiService.doPost(Consts.QUERY_ADD_FAVORITE, data).subscribe(
            (data) => {
                this.errors = {};
            },
            (error) => {
                this.errors = error;
            }
        );
    }

    navigateToResultEdit(result) {
        if (result.type == 'sample') {
            this.router.navigate(['/home/samples/sample', result.id]);
        }
        if (result.type == 'publication') {
            this.router.navigate([
                '/home/samples/publications/publication',
                result.id,
            ]);
        }
        if (result.type == 'protocol') {
            this.router.navigate(['/home/protocols/edit-protocol', result.id]);
        }
    }

    navigateToResultView(result) {
        if (result.type == 'sample') {
            this.router.navigate(['/home/samples/view-sample', result.id]);
        }
        if (result.type == 'publication') {
            console.log(result)
        }
        if (result.type == 'protocol') {
            window.open(Consts.QUERY_DOWNLOAD_FILE+'?fileId='+result.fileId)
        }
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
        this.setupPage();
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
                this.searchResults.sort((a, b) => (this.getStringValue(a[key],key) > this.getStringValue(b[key],key) ? 1 : -1));
            } else {
                this.searchResults.sort((a, b) => (this.getStringValue(a[key],key) < this.getStringValue(b[key],key) ? 1 : -1));
            }
            this.setupPage();
        }
    }

    getStringValue(value,key) {
        if (value) {
            if (key=='createdDate') { // first convert the date string to integer //
                value=new Date(value).getTime();
            }
            return value.toString().toUpperCase()
        }
        return ''
    }

    setupPage() {
        this.searchResultsPageToDisplay = this.searchResults.slice(
            this.pageLength * this.currentPage,
            this.pageLength * (this.currentPage + 1)
        );
    }
}
