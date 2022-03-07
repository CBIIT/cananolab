// --------------------------------------------------------------------------------------------
// ------ This is the Publications screen which is called from  -------------------------------
// ------ the top left "Navigation Tree" menu when a specific Sample has been selected. -------
// ------ Not to be confused with "Publications" in the top horizontal menu.  -----------------
// --------------------------------------------------------------------------------------------

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';

@Component( {
    selector: 'canano-sample-publications',
    templateUrl: './sample-publications.component.html',
    styleUrls: ['./sample-publications.component.scss']
} )
export class SamplePublicationsComponent implements OnInit{
    sampleId = Properties.CURRENT_SAMPLE_ID;
    sampleName = Properties.CURRENT_SAMPLE_NAME;
    helpUrl = Consts.HELP_URL_SAMPLE_PUBLICATIONS;
    toolHeadingNameManage = 'Sample ' + this.sampleName + ' Publication';
    constructor( private route: ActivatedRoute ){
    }

    ngOnInit(): void{

    }

}
