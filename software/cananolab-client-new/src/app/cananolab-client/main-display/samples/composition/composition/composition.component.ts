import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';

@Component( {
    selector: 'canano-composition',
    templateUrl: './composition.component.html',
    styleUrls: ['./composition.component.scss']
} )
export class CompositionComponent implements OnInit{
    sampleId = Properties.CURRENT_SAMPLE_ID;
    sampleName = Properties.CURRENT_SAMPLE_NAME;
    helpUrl =  Consts.HELP_URL_SAMPLE_COMPOSITION;
    toolHeadingNameManage = 'Sample Composition';

    constructor( private route: ActivatedRoute ){
    }

    ngOnInit(): void{
    }

}
