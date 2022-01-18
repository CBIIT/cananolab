import { Component, OnDestroy, OnInit } from '@angular/core';
import { SampleSearchResultsService } from './sample-search-results.service';
import { Consts, SortState } from '../../../../../constants';
import { Properties } from '../../../../../../assets/properties';
import { takeUntil, timeout } from 'rxjs/operators';
import { SearchResultsPagerService } from '../../../../common/components/search-results-pager/search-results-pager.service';
import { Subject } from 'rxjs';
import { StatusDisplayService } from '../../../../status-display/status-display.service';
import { ApiService } from '../../../../common/services/api.service';
import { SampleAvailabilityDisplayService } from './sample-availability-display/sample-availability-display.service';
import { Router } from '@angular/router';

@Component( {
    selector: 'canano-sample-search-results',
    templateUrl: './sample-search-results.component.html',
    styleUrls: ['./sample-search-results.component.scss']
} )
export class SampleSearchResultsComponent implements OnInit, OnDestroy{
    columnHeadings = ['Actions', 'Sample Name', 'Primary Point of Contact', 'Composition', 'Functions', 'Characterizations', 'Data Availability', 'Created'];

    maxPageLength = Properties.MAX_PAGE_LENGTH;
    pageLength = Properties.DEFAULT_PAGE_LENGTH;

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

    constructor( private sampleSearchResultsService: SampleSearchResultsService, private searchResultsPagerService: SearchResultsPagerService,
                 private statusDisplayService: StatusDisplayService, private apiService: ApiService,
                 private router: Router, private sampleAvailabilityDisplayService: SampleAvailabilityDisplayService ){
    }

    ngOnInit(): void{
        this.searchResults = this.sampleSearchResultsService.getSearchResults();
        this.searchResultsCount = this.searchResults.length;

        console.log( 'MHL searchResults: ', this.searchResults );


        this.searchResultsPagerService.currentPageChangeEmitter.pipe( takeUntil( this.ngUnsubscribe ) )
            .subscribe( ( data ) => {
                console.log( 'MHL SampleSearchResultsComponent currentPageChangeEmitter data: ', data );
                this.currentPage = data;
                this.setupPage();
            } );


        this.statusDisplayService.updateUserEmitter.pipe( timeout( Properties.HTTP_TIMEOUT ) ).subscribe(
            data => {
                this.userName = data;
                console.log( 'MHL updateUserEmitter: ', this.userName );
            } );


        this.searchResultsCount = this.searchResults.length;
        this.pageCount = Math.ceil( this.searchResultsCount / this.pageLength );
        this.onPageLengthChange();
    }


    navigateToSampleEdit( sampleId ){
        console.log('MHL navigateToSampleEdit: ', sampleId);
        this.router.navigate(['home/samples/samplesEdit', '?sampleId=' + sampleId ]);  // @FIXME  Don't hard code these
    }

    navigateToSampleView(sampleId, sampleName){
        console.log('MHL navigateToSampleView: ', sampleId + ' : ' + sampleName);
        this.router.navigate(['home/samples/samplesView', '?sampleId=' + sampleId + '&sampleName=' + sampleName ]);  // @FIXME  Don't hard code these
    }


    addToFavorites(){
        console.log('MHL addToFavorites');
    }

    onAvailabilityClick( id ){
        console.log('MHL onAvailabilityClick: ', id);
// QUERY_SAMPLE_AVAILABILITY
        this.apiService.doGet( Consts.QUERY_SAMPLE_AVAILABILITY, 'sampleId=' + id).subscribe(
            data => {
                console.log( 'MHL QUERY_SAMPLE_AVAILABILITY: ', data );
                this.sampleAvailabilityDisplayService.displayStuff( data );
            },
            ( err ) => {
                console.log( 'MHL ERROR QUERY_SAMPLE_AVAILABILITY: ', err );
            } );

    }

    /*
http://cent16:8090/caNanoLab/rest/sample/viewDataAvailability?sampleId=25799936

    $scope.openDataAvailability = function (sampleId) {
      $http({
        method: 'GET',
        url: '/caNanoLab/rest/sample/viewDataAvailability',
        params: {
          "sampleId": sampleId
        }
      }).
      then(function (data, status, headers, config) {
        data = data['data']
        var modalInstance = $modal.open({
          templateUrl: 'views/sample/view/sampleDataAvailability.html',
          controller: 'SampleDataAvailabilityCtrl',
          size: 'sm',
          resolve: {
            sampleId: function () {
              return sampleId;
            },
            availabilityData: function () {
              return data;
            },
            sampleData: function () {
              return data;
            },
            edit: function () {
              return 0;
            }
          }
        });
      }).
      catch(function (data, status, headers, config) {
        data = data['data']
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        $scope.message = data;
      });
    };


     */


    setupPage(){
        console.log( 'MHL setupPage pageLength: ', this.pageLength );
        console.log( 'MHL setupPage currentPage: ', this.currentPage );
        this.searchResultsPageToDisplay = this.searchResults.slice( this.pageLength * this.currentPage, this.pageLength * (this.currentPage + 1) );

        /*
        //  Title:<br>zzzz<br><br>Description:<br>zzzzz<br><br><a href=rest/protocol/download?fileId=98238465 target='new'>View</a>
                for( let f = 0; f < this.searchResultsPageToDisplay.length; f++){
                    console.log('MHL 801 fileInfo: ', this.searchResultsPageToDisplay[f].fileInfo);  // sed with Properties API_SERVER_URL
                    this.parseFileData( this.searchResultsPageToDisplay[f].fileInfo);
                    this.searchResultsPageToDisplay[f].fileInfo = this.searchResultsPageToDisplay[f].fileInfo.replace('href=', 'href=' + Properties.API_SERVER_URL + '/');
                    console.log('MHL 802 fileInfo: ', this.searchResultsPageToDisplay[f].fileInfo);  // sed with Properties API_SERVER_URL
                }
        */

    }


    onPageLengthChange(){
        console.log( 'MHL 999a >>>>>>>>>>>>> ' );
        if( this.pageLength < 1 ){
            this.pageLength = 1;
        }
        if( this.pageLength > this.maxPageLength ){
            this.pageLength = this.maxPageLength;
        }

        this.pageCount = Math.ceil( this.searchResultsCount / this.pageLength );
        console.log( 'MHL 300 SampleSearchResultsComponent.onPageLengthChange searchResultsCount: ', this.searchResultsCount );
        console.log( 'MHL 301 SampleSearchResultsComponent.onPageLengthChange pageLength: ', this.pageLength );
        console.log( 'MHL 302 SampleSearchResultsComponent.onPageLengthChange pageCount: ', this.pageCount );
        this.searchResultsPagerService.setPageCount( this.pageCount );
        this.setupPage(); // Sets this page as the currently vied search results.
        console.log( 'MHL 999b <<<<<<<<<<<<<< ' );
    }

    onSortClick( i ){
        console.log( 'MHL onSortClick: ', i );
    }


    ngOnDestroy(){
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }
}
