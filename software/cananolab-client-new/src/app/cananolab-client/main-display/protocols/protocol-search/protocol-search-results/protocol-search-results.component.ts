//

import { Component, OnDestroy, OnInit } from '@angular/core';
import { SearchResultsPagerService } from '../../../../common/components/search-results-pager/search-results-pager.service';
import { takeUntil, timeout } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { UtilService } from '../../../../common/services/util.service';
import { Consts, ProtocolScreen, SortState } from '../../../../../constants';
import { Properties } from '../../../../../../assets/properties';
import { ProtocolsService } from '../../protocols.service';
import { ApiService } from '../../../../common/services/api.service';
import { StatusDisplayService } from '../../../../status-display/status-display.service';
import { Router } from '@angular/router';
@Component({
    selector: 'canano-protocol-search-results',
    templateUrl: './protocol-search-results.component.html',
    styleUrls: ['./protocol-search-results.component.scss'],
})
export class ProtocolSearchResultsComponent implements OnInit, OnDestroy {
    searchResults;
    columnHeadings = [
        {Actions:'Actions'},
        {type:'Protocol Type'},
        {viewName:'Protocol Name'},
        {abbreviation:'Abbreviation'},
        {version:'Version'},
        {fileInfo:'File'},
        {createdDate:'Created'}
    ];
    maxPageLength = Properties.MAX_PAGE_LENGTH;
    pageLength = Properties.DEFAULT_PAGE_LENGTH;
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
        SortState.NO_SORT
    ];

    sortState = SortState;
    properties = Properties;

    fileTitle = '';
    fileDescription = '';
    fileHref = '';
    userName = 'X';

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor(
        private router: Router,
        private searchResultsPagerService: SearchResultsPagerService,
        private utilService: UtilService,
        private protocolsService: ProtocolsService,
        private apiService: ApiService,
        private statusDisplayService: StatusDisplayService
    ) {}

    ngOnInit() {
        this.searchResults = this.protocolsService.getProtocolSearchResults();
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

    setupPage() {
        this.searchResultsPageToDisplay = this.searchResults.slice(
            this.pageLength * this.currentPage,
            this.pageLength * (this.currentPage + 1)
        );
    }

    parseFileData(fileData: string) {}

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
            if (this.sortingStates[i] == 1) {
                this.searchResults.sort((a, b) => (a[key] < b[key] ? 1 : -1));
            } else {
                this.searchResults.sort((a, b) => (a[key] > b[key] ? 1 : -1));
            }
            this.setupPage();
        }
    }

    onEditClick(protocolToEdit) {
        this.router.navigate([
            'home/protocols/edit-protocol',
            protocolToEdit.id,
        ]);
        // this.protocolsService.setCurrentProtocolScreen( ProtocolScreen.PROTOCOL_EDIT_SCREEN, protocolToEdit.id );
    }

    onViewClick(protocolToView) {
        this.protocolsService.setCurrentProtocolScreen(
            ProtocolScreen.PROTOCOL_VIEW_SCREEN,
            protocolToView.id
        );
    }

    addToFavorites(
        protocolId,
        protocolName,
        fileId,
        editable,
        protocolFileTitle
    ) {
        // api.doPost
        let queryData = {
            dataType: 'protocol',
            dataName: protocolName,
            dataId: protocolId,
            protocolFileId: fileId,
            protocolFileTitle: protocolFileTitle,
            editable: true,
            loginName: 'canano_curator',
        };

        this.apiService
            .doPost(Consts.QUERY_ADD_FAVORITE, <any>queryData)
            .subscribe(
                (data) => {
                    console.log('Data back from addToFavorites: ', data);
                },
                (err) => {
                    console.error('ERR Data back from addToFavorites: ', err);
                }
            );
    }

    ngOnDestroy() {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }
}
