import { Component, Input, OnInit } from '@angular/core';
import { Properties } from 'src/assets/properties';
import { Consts } from 'src/app/constants';
import { SortState } from 'src/app/constants';
import { Router } from '@angular/router';
import { SearchResultsPagerService } from 'src/app/cananolab-client/common/components/search-results-pager/search-results-pager.service';
import { UtilService } from 'src/app/cananolab-client/common/services/util.service';
import { StatusDisplayService } from 'src/app/cananolab-client/status-display/status-display.service';
import { takeUntil,timeout } from 'rxjs/operators';
import { Subject } from 'rxjs';
@Component({
  selector: 'canano-user-results',
  templateUrl: './user-results.component.html',
  styleUrls: ['./user-results.component.scss']
})
export class UserResultsComponent implements OnInit {
    @Input() searchResults;
    columnHeadings = [
        {Actions:'Actions'},
        {username:'Username'},
        {firstName:'First Name'},
        {lastName:'Last Name'},
        {organization:'Organization'},
        {department:'Department'},
        {title:'Title'},
        {phoneNumber:"Phone"},
        {emailId:"Email"}
    ];

    maxPageLength = Properties.MAX_PAGE_LENGTH;
    pageLength = Properties.DEFAULT_PAGE_LENGTH;
    pageCount = 10;
    searchResultsCount = -1;
    currentPage = 0;
    searchResultsPageToDisplay;
    userName;

    sortingStates = [
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
        SortState.NO_SORT,
    ];

    sortState = SortState;
    properties = Properties;
    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor(
        private router: Router,
        private searchResultsPagerService: SearchResultsPagerService,
        private utilService: UtilService,
        private statusDisplayService: StatusDisplayService
    ) { }

    ngOnInit(): void {
        this.searchResultsPagerService.currentPageChangeEmitter
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe((data) => {
                this.currentPage = data;
                this.setupPage();
            });

        this.statusDisplayService.updateUserEmitter
            .pipe(timeout(Properties.HTTP_TIMEOUT))
            .subscribe((data) => {
                this.userName = Properties.current_user;
            });
            this.searchResultsCount = this.searchResults.length;
            this.pageCount = Math.ceil(this.searchResultsCount / this.pageLength);
            this.onPageLengthChange();
    }

    setupPage() {
        this.searchResultsPageToDisplay = this.searchResults.slice(
            this.pageLength * this.currentPage,
            this.pageLength * (this.currentPage + 1)
        );
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

    navigateToEditUser(user) {
        this.router.navigate(['/home/admin/update-user',user])
    }

    navigateToResetPassword(user) {
        this.router.navigate(['/home/admin/reset-password',user])
    }

    getStringValue(val) {
        if (val) {
            return val.toString().toUpperCase()
        }
        return ''
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
        this.setupPage(); // Sets this page as the currently viewed search results.
    }

    ngOnDestroy() {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

}
