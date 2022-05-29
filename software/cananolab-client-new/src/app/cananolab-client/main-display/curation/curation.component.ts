import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Properties } from '../../../../assets/properties';
import { Consts } from '../../../constants';
import { ConsoleReporter } from 'jasmine';

@Component({
  selector: 'canano-curation',
  templateUrl: './curation.component.html',
  styleUrls: ['./curation.component.scss']
})
export class CurationComponent implements OnInit {
    data;
    helpUrl='https://wiki.nci.nih.gov/display/caNanoLab/Managing+Data+Curation#ManagingDataCuration-ReviewBatchResults';
    toolHeadingNameManage = 'Review By Curator';
    tempCuration=[
        {
           "dataId":"53346305",
           "dataName":"UChicago-KLuJACS2015-01",
           "dataType":"sample",
           "submittedDate":"2015-11-22",
           "submittedBy":"chunbaih",
           "reviewStatus":"pending",
           "reviewLink":"/caNanoLab/#/editSample?sampleId=53346305"},
           {
            "dataId":"53346305",
            "dataName":"UChicago-KLuJACS2015-01",
            "dataType":"sample",
            "submittedDate":"2015-11-22",
            "submittedBy":"chunbaih",
            "reviewStatus":"pending",
            "reviewLink":"/caNanoLab/#/editSample?sampleId=53346305"}        ]
    constructor(private httpClient:HttpClient) { }

    ngOnInit(): void {
        let url=this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/curation/reviewData');
        url.subscribe(data=> {
            this.data=data;
            this.data=this.tempCuration
        },
        error=> {
            console.log('error')
        })
    }



}
