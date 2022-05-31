import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../../constants';
import { ApiService } from '../../../../common/services/api.service';
import { Router } from '@angular/router';

@Component( {
    selector: 'canano-sample-copy',
    templateUrl: './sample-copy.component.html',
    styleUrls: ['./sample-copy.component.scss']
} )
export class SampleCopyComponent implements OnInit{

    helpUrl = Consts.HELP_URL_SAMPLE_COPY;
    toolHeadingNameSearchSample = 'Copy Sample';

    sampleName = '';
    newSampleName = '';

    sampleNames;
    showNamesMenu = false;

    constructor( private apiService: ApiService, private router: Router ){
    }

    ngOnInit(): void{
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

    onSubmitCopyClicked(){
        this.apiService.doPost( Consts.QUERY_SAMPLE_COPY, { 'newSampleName': this.newSampleName, 'sampleName': this.sampleName } ).subscribe(
            data => {
                // console.log( 'Copy sample: ', data );
            },
            ( err ) => {
                console.log( 'ERROR Copy sample: ', err );
                // alert('Error Copy sample: ' + err['error'][0] ); // @TODO need nice error display
            }
        );
    }
}
