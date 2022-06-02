import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Properties } from '../../../../assets/properties';
import { Router } from '@angular/router';

@Component({
  selector: 'canano-curation',
  templateUrl: './curation.component.html',
  styleUrls: ['./curation.component.scss']
})
export class CurationComponent implements OnInit {
    data;
    errors={};
    helpUrl='https://wiki.nci.nih.gov/display/caNanoLab/Managing+Data+Curation#ManagingDataCuration-ReviewBatchResults';
    toolHeadingNameManage = 'Review By Curator';
    constructor(private router:Router,private httpClient:HttpClient) { }

    ngOnInit(): void {
        let url=this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/curation/reviewData');
        url.subscribe(data=> {
            this.data=data;
            this.errors={};
        },
        error=> {
            this.errors=error;
            console.log('error')
        })
    }

    edit(record) {
        if (record.dataType=='sample') {
            this.router.navigate(['/home/samples/sample',record.dataId]);
        };
        if (record.dataType=='protocol') {
            this.router.navigate(['/home/protocols/edit-protocol',record.dataId]);
        };
        if (record.dataType=='publication') {
            this.router.navigate(['/home/samples/publications/publication',record.dataId]);
        };
    };
}
