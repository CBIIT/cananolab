import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Consts } from 'src/app/constants';
import { ApiService } from '../../common/services/api.service';
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
    constructor(private apiService:ApiService,private router:Router) { }

    ngOnInit(): void {
        let url=this.apiService.doGet(Consts.QUERY_CURATION_REVIEW_DATA,'');
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
