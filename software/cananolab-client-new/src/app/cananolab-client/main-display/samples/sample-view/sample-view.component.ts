import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../constants';
import { ActivatedRoute, Params } from '@angular/router';

@Component( {
    selector: 'canano-sample-view',
    templateUrl: './sample-view.component.html',
    styleUrls: ['./sample-view.component.scss']
} )
export class SampleViewComponent implements OnInit{
    sampleId = -1;
    sampleName = '';

    helpUrl = Consts.HELP_URL_SAMPLE_VIEW;
    toolHeadingNameViewSample = 'Sample ' + this.sampleName;

    constructor( private route: ActivatedRoute ){
    }

    ngOnInit(): void{

        this.route.params.subscribe(
            ( params: Params ) => {
                this.sampleId = params['sampleId'].replace( /^.*\?sampleId=/, '' ).replace(/&.*$/, '');
                this.sampleName = params['sampleId'].replace( /^.*sampleName=/, '' );
                this.toolHeadingNameViewSample = 'Sample ' + this.sampleName;
            } );
    }
}
