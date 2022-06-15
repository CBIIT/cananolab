// -----------------------------------------------------------------------------------------
// ----------------------    The Search at top right (just stubs)   ------------------------
// -----------------------------------------------------------------------------------------

import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../common/services/api.service';
import { Consts } from '../../../constants';
import { TopKeywordSearchService } from './top-keyword-search.service';
import { Router } from '@angular/router';
@Component( {
    selector: 'canano-top-keyword-search',
    templateUrl: './top-keyword-search.component.html',
    styleUrls: ['./top-keyword-search.component.scss']
} )
export class TopKeywordSearchComponent implements OnInit{
    topKeyWordSearchText = '';
    loading;
    loadingMessage=Consts.searchingMessage;
    constructor( private router:Router,private topKeywordSearchService:TopKeywordSearchService,private apiService: ApiService ){
    }

    ngOnInit(): void{
    }

    onTopKeyWordSearchClick(){
        this.loading=true;

        //  @TODO
        this.apiService.doGet( Consts.QUERY_SEARCH, 'keyword=' + this.topKeyWordSearchText ).subscribe(
            data => {
                this.topKeywordSearchService.setKeywordResults(data);
                this.router.navigateByUrl('home', {skipLocationChange: true}).then(()=>
                this.router.navigate(['home/search-results']));
                this.loading=null;
            },
            ( err ) => {
                this.loading=null;
                console.log( 'ERROR onTopKeyWordSearchClick: ', err );
            } );
    }

    /**
     *
     */
    getUsers(){
        // security/gets

        //  this.apiService.doPost( Consts.QUERY_SEARCH, 'keyword=' + this.topKeyWordSearchText ).subscribe(
        this.apiService.doGet( Consts.QUERY_GET_USERS, '*' ).subscribe(
            data => {
                console.log( 'getUsers data: ', data );
            },
            ( err ) => {
                console.log( 'ERROR getUsers err: ', err );
            } );

    }
}
