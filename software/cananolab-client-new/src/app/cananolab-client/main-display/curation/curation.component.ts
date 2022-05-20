import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Properties } from '../../../../assets/properties';
import { Consts } from '../../../constants';

@Component({
  selector: 'canano-curation',
  templateUrl: './curation.component.html',
  styleUrls: ['./curation.component.scss']
})
export class CurationComponent implements OnInit {
  data;
  helpUrl='https://wiki.nci.nih.gov/display/caNanoLab/Managing+Data+Curation#ManagingDataCuration-ReviewBatchResults';
  toolHeadingNameManage = 'Review By Curator';

  constructor(private httpClient:HttpClient) { }

  ngOnInit(): void {
      let url=this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/curation/reviewData');
      url.subscribe(data=> {
        this.data=data;
      },
      error=> {
          console.log('error')
      })
  }



}
