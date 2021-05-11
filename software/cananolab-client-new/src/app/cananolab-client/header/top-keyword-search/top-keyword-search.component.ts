import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../common/services/api.service';
import { Consts } from '../../../constants';

@Component({
  selector: 'canano-top-keyword-search',
  templateUrl: './top-keyword-search.component.html',
  styleUrls: ['./top-keyword-search.component.scss']
})
export class TopKeywordSearchComponent implements OnInit {
    topKeyWordSearchText = '';
  constructor( private apiService: ApiService) { }

  ngOnInit(): void {
  }

    onTopKeyWordSearchClick(){
      console.log('MHL onTopKeyWordSearchClick topKeyWordSearchText: ', this.topKeyWordSearchText );
      // /caNanoLab/rest/customsearch/search?keyword={{topKeyWordSearchText}}


      //  this.apiService.doPost( Consts.QUERY_SEARCH, 'keyword=' + this.topKeyWordSearchText ).subscribe(
        this.apiService.doGet( Consts.QUERY_SEARCH, 'keyword=' + this.topKeyWordSearchText ).subscribe(
            data => {
            console.log('MHL onTopKeyWordSearchClick: ', data);
                // @TESTING
                this.getUserGroups()
            },
            ( err ) => {
                console.log('MHL ERROR onTopKeyWordSearchClick: ', err);
            });
    }

    // @TESTING
    getUserGroups(){ // security/getUserGroups
        console.log('MHL getUserGroups');

        //  this.apiService.doPost( Consts.QUERY_SEARCH, 'keyword=' + this.topKeyWordSearchText ).subscribe(
        this.apiService.doGet( Consts.QUERY_GET_USER_GROUPS, '' ).subscribe(
            data => {
                console.log('MHL getUserGroups: ', data);
            },
            ( err ) => {
                console.log('MHL ERROR getUserGroups: ', err);
            });

    }
}
