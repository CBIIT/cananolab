import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Consts } from 'src/app/constants';
import { ApiService } from '../../common/services/api.service';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'canano-curation',
  templateUrl: './curation.component.html',
  styleUrls: ['./curation.component.scss']
})
export class CurationComponent implements OnInit {
    data;
    errors={};
    message='';
    currationResults;
    currentUrl = 'curation';
    generateOptions={option:""};
    helpUrl='https://wiki.nci.nih.gov/display/caNanoLab/Managing+Data+Curation';
    toolHeadingNameManage = 'Manage Curation';
    constructor(private activatedRoute:ActivatedRoute,private apiService:ApiService,private router:Router) { }

    ngOnInit(): void {
        if (this.router.url.includes('manage-availability')) {
            this.currentUrl = 'manage-availability';
            this.helpUrl='https://wiki.nci.nih.gov/display/caNanoLab/Managing+Data+Curation#ManagingDataCuration-ManageBatch';
            this.toolHeadingNameManage='Manage Batch Data Availability';
        }
        if (this.router.url.includes('review-data')) {
            this.currentUrl = 'review-data';
            this.helpUrl='https://wiki.nci.nih.gov/display/caNanoLab/Managing+Data+Curation#ManagingDataCuration-ReviewBatchResults';
            this.toolHeadingNameManage='Review By Curator';
        }
        let reviewDataUrl=this.apiService.doGet(Consts.QUERY_CURATION_REVIEW_DATA,'');
        if (this.currentUrl=='review-data') {
            reviewDataUrl.subscribe(data=> {
                this.data=data;
                this.errors={};
            },
            error=> {
                this.errors=error;
            })
        }

        if (this.router.url.includes('results')) {
            this.currentUrl = 'results';
            this.helpUrl='https://wiki.nci.nih.gov/display/caNanoLab/Managing+Data+Curation';
            this.toolHeadingNameManage='Long Running Processes';
            this.getCurationResults();
        }

        let generateUrl=this.apiService.doPost(Consts.QUERY_CURATION_GENERATE_BATCH_AVAILABILITY,this.generateOptions);


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

    resetGenerateOptions() {
        this.generateOptions={option:""}
    }

    submitGenerateOptions() {
        let url = this.apiService.doPost(Consts.QUERY_CURATION_GENERATE_BATCH_AVAILABILITY,this.generateOptions);
        url.subscribe(data=> {
            this.message=data;
        })
    }

    getCurationResults() {
        this.apiService.doGet(Consts.QUERY_CURATION_RESULTS,'').subscribe(data=> {
            this.currationResults=data;
        })
    }
}
