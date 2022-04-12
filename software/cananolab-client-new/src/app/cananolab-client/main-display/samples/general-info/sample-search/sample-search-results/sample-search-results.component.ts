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


    navigateToSampleEdit( sampleId ){
        this.router.navigate(['home/samples/samplesEdit', '?sampleId=' + sampleId ]);  // @FIXME  Don't hard code these
    }

    navigateToSampleView(sampleId, sampleName){
        this.router.navigate(['home/samples/samplesView', '?sampleId=' + sampleId + '&sampleName=' + sampleName ]);  // @FIXME  Don't hard code these
    }


    addToFavorites(samp){
        console.log('addToFavorites samp: ', samp);
        let favObj = {'dataType': 'sample', 'loginName': 'canano_curator'};  // @FIXME User real user name
        favObj['dataName'] = samp['sampleName'];
        favObj['dataId'] = samp['sampleId'];
        favObj['description'] = samp['nanoEntityDesc'];
        favObj['editable'] = samp['editable'];
        console.log('favObj:', favObj);

        this.apiService.doPost( Consts.QUERY_ADD_FAVORITE, favObj ).subscribe(
            data => {
                console.log('MHL set Fave results: ', data);
            },
            err => {
                console.log('MHL ERROR QUERY_ADD_FAVORITE: ', err);
            }
        );
    }

    onAvailabilityClick( id ){
        this.apiService.doGet( Consts.QUERY_SAMPLE_AVAILABILITY, 'sampleId=' + id).subscribe(
            data => {
                this.sampleAvailabilityDisplayService.displayStuff( data );
            },
            ( err ) => {
                console.log( 'ERROR QUERY_SAMPLE_AVAILABILITY: ', err );
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
        this.searchResultsPageToDisplay = this.searchResults.slice( this.pageLength * this.currentPage, this.pageLength * (this.currentPage + 1) );

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


    ngOnDestroy(){
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }
}
