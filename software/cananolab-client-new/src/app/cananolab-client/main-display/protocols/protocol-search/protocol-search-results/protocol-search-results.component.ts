//

import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { SearchResultsPagerService } from '../../../../common/components/search-results-pager/search-results-pager.service';
import { takeUntil, timeout } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { UtilService } from '../../../../common/services/util.service';
import { Consts, ProtocolScreen, SortState } from '../../../../../constants';
import { Properties } from '../../../../../../assets/properties';
import { ProtocolsService } from '../../protocols.service';
import { ApiService } from '../../../../common/services/api.service';
import { StatusDisplayService } from '../../../../status-display/status-display.service';
@Component( {
    selector: 'canano-protocol-search-results',
    templateUrl: './protocol-search-results.component.html',
    styleUrls: ['./protocol-search-results.component.scss']
} )
export class ProtocolSearchResultsComponent implements OnInit, OnDestroy{
    searchResults;
    columnHeadings = ['Actions', 'Type', 'Name', 'Abbreviation', 'Version', 'File', 'Created'];
    maxPageLength = Properties.MAX_PAGE_LENGTH;
    pageLength = Properties.DEFAULT_PAGE_LENGTH;
    pageCount = 10;
    searchResultsCount = -1;
    currentPage = 0;
    searchResultsPageToDisplay;

    sortingStates = [SortState.NO_SORT, SortState.SORT_UP, SortState.NO_SORT, SortState.NO_SORT, SortState.NO_SORT, SortState.NO_SORT, SortState.NO_SORT];

    sortState = SortState;
    properties = Properties;

    fileTitle = '';
    fileDescription = '';
    fileHref = '';
    userName = 'X';

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private searchResultsPagerService: SearchResultsPagerService, private utilService: UtilService,
                 private protocolsService: ProtocolsService, private apiService: ApiService, private statusDisplayService: StatusDisplayService ){
    }

    ngOnInit(){
        this.searchResults=this.protocolsService.getProtocolSearchResults();
        this.searchResultsPagerService.currentPageChangeEmitter.pipe( takeUntil( this.ngUnsubscribe ) )
            .subscribe( ( data ) => {
                this.currentPage = data;
                this.setupPage();
            } );




        this.statusDisplayService.updateUserEmitter.pipe( timeout( Properties.HTTP_TIMEOUT ) ).subscribe(
            data => {
                this.userName = data;
            });



        this.searchResultsCount = this.searchResults.length;
        this.pageCount = Math.ceil( this.searchResultsCount / this.pageLength );
        this.onPageLengthChange();
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
        this.setupPage(); // Sets this page as the currently viewed search results.
    }

    setupPage(){
        this.searchResultsPageToDisplay = this.searchResults.slice( this.pageLength * this.currentPage, this.pageLength * (this.currentPage + 1) );

    }

    parseFileData( fileData: string ){


    }

    onSortClick( c ){
        // No sorting for "Actions" column
        if( c < 1 ){
            return;
        }

        let temp = -1;

        // Set temp to new setting
        switch( this.sortingStates[c] ){
            case SortState.NO_SORT:
                temp = SortState.SORT_UP;
                break;
            case SortState.SORT_UP:
                temp = SortState.SORT_DOWN;
                break;
            case SortState.SORT_DOWN:
                temp = SortState.SORT_UP;
                break;
        }
        // END Set temp to new setting

        // Clear what ever may have been set
        for( let n = 0; n < this.sortingStates.length; n++ ){
            this.sortingStates[n] = SortState.NO_SORT;
        }

        // Set the ne sort state
        this.sortingStates[c] = temp;

        this.doSort();
    }

    onEditClick( protocolToEdit ){
        this.protocolsService.setCurrentProtocolScreen( ProtocolScreen.PROTOCOL_EDIT_SCREEN, protocolToEdit.id );
    }

    onViewClick( protocolToView ){
        this.protocolsService.setCurrentProtocolScreen( ProtocolScreen.PROTOCOL_VIEW_SCREEN, protocolToView.id );
    }

    doSort(){
        let column = -1;
        let dir = -1;
        for( let n = 0; n < this.sortingStates.length; n++ ){
            if( this.sortingStates[n] !== SortState.NO_SORT ){
                dir = this.sortingStates[n];
                column = n;
            }

            // Type
            switch( column ){
                case 1:
                    this.searchResults.sort( ( row1, row2 ) =>
                        row1.type.localeCompare( row2.type ) *
                        (dir === SortState.SORT_DOWN ? -1 : 1)
                    );
                    break;

                // viewName "Name"
                case 2:
                    this.searchResults.sort( ( row1, row2 ) =>
                        row1.viewName.localeCompare( row2.viewName ) *
                        (dir === SortState.SORT_DOWN ? -1 : 1)
                    );
                    break;

                // abbreviation
                case 3:
                    this.searchResults.sort( ( row1, row2 ) =>
                        row1.abbreviation.localeCompare( row2.abbreviation ) *
                        (dir === SortState.SORT_DOWN ? -1 : 1)
                    );
                    break;

                // Version
                case 4:
                    this.searchResults.sort( ( n1, n2 ) => (n1.version - n2.version) *
                        (dir === SortState.SORT_DOWN ? -1 : 1)
                    );
                    break;

                // fileInfo
                case 5:
                    this.searchResults.sort( ( row1, row2 ) =>
                        row1.fileInfo.localeCompare( row2.fileInfo ) *
                        (dir === SortState.SORT_DOWN ? -1 : 1)
                    );
                    break;

                // createdDate
                case 6:
                    this.searchResults.sort( ( row1, row2 ) => row2.createdDate - row1.createdDate *
                        (dir === SortState.SORT_DOWN ? -1 : 1)
                    );
                    break;
            }

        }
        this.setupPage();
    }

    addToFavorites( protocolId, protocolName, fileId, editable, protocolFileTitle ){

        // api.doPost
        let queryData = {
            dataType: 'protocol',
            dataName: protocolName,
            dataId: protocolId,
            protocolFileId: fileId,
            protocolFileTitle: protocolFileTitle,
            editable: true,
            loginName: 'canano_curator'
        };

        this.apiService.doPost( Consts.QUERY_ADD_FAVORITE, <any>queryData ).subscribe(
            data => {
                console.log( 'Data back from addToFavorites: ', data );
            },
            err => {
                console.error( 'ERR Data back from addToFavorites: ', err );
            }
        );
    }

    /*

        $scope.addToFavorites = function (protocolId, protocolName, fileId, editable, protocolFileTitle) {
            $scope.loader = true;

            $scope.favoriteBean = {
                "dataType": "protocol",
                "dataName": protocolName,
                "dataId": protocolId,
                "protocolFileId": fileId,
                "editable": editable,
                "protocolFileTitle": protocolFileTitle,
                "loginName": $scope.loggedInUser.name
            };

            $http({
                method: 'POST',
                url: '/caNanoLab/rest/core/addFavorite',
                data: $scope.favoriteBean
            }).
            then(function (data, status, headers, config) {
                data = data['data']
                var hrefEl = document.getElementById('href' + protocolId);
                var msgEl = document.getElementById('msg' + protocolId);
                msgEl.innerHTML = data;
                hrefEl.style.visibility = "hidden";
                $scope.loader = false;

            }).
            catch(function (data, status, headers, config) {
                data = data['data']
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                // $rootScope.sampleData = data;
                $scope.loader = false;
                $scope.messages = data;
            });
        };
     */

    ngOnDestroy(){
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }
}
