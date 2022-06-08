import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../../constants';
import { ApiService } from '../../../../common/services/api.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
@Component( {
    selector: 'canano-sample-copy',
    templateUrl: './sample-copy.component.html',
    styleUrls: ['./sample-copy.component.scss']
} )
export class SampleCopyComponent implements OnInit{

    helpUrl = Consts.HELP_URL_SAMPLE_COPY;
    toolHeadingNameSearchSample = 'Copy Sample';

    sampleName = '';
    sampleId;
    newSampleName = '';
    errors={};
    sampleNames;
    showNamesMenu = false;

    constructor( private activatedRoute:ActivatedRoute,private apiService: ApiService, private router: Router ){
    }

    ngOnInit(): void{
        this.activatedRoute.params.subscribe(data=> {
            this.sampleId=data['sampleId'];
            if (this.sampleId) {
                this.apiService.doGet(Consts.QUERY_SAMPLE_GET_SAMPLE_NAME,'sampleId='+this.sampleId).subscribe(data=> {
                    this.sampleName=data['sampleName']
                })
            }
        })
        this.init();
    }

    init(){
        this.apiService.doGet( Consts.QUERY_SAMPLE_GET_NAMES, '' ).subscribe(
            data => {
                this.sampleNames = data;
            },
            ( err ) => {
                console.log( 'ERROR SampleCopyComponent init QUERY_SAMPLE_GET_NAMES: ', err );
            }
        );
    }

    browseClicked(){
        this.showNamesMenu = ! this.showNamesMenu;
    }

    navigateToSampleEdit( sampleId ){
        this.router.navigate(['home/samples/sample', sampleId ]);  // @FIXME  Don't hard code these
    }

    selectSampleName( nm ){
        this.sampleName = nm;
    }

    reset() {
        this.sampleName='';
        this.newSampleName='';
    }

    onSubmitCopyClicked(){
        this.apiService.doPost( Consts.QUERY_SAMPLE_COPY, { 'newSampleName': this.newSampleName, 'sampleName': this.sampleName } ).subscribe(
            data => {
                this.router.navigate(['home/samples/sample',data.sampleId])
            },
            ( err ) => {
                this.errors=err;
            }
        );
    }
}
