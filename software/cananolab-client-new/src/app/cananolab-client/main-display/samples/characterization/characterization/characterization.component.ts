import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';

@Component( {
    selector: 'canano-characterization',
    templateUrl: './characterization.component.html',
    styleUrls: ['./characterization.component.scss']
} )
export class CharacterizationComponent implements OnInit{
    sampleId = Properties.CURRENT_SAMPLE_ID;
    sampleName = Properties.CURRENT_SAMPLE_NAME;
    helpUrl = Consts.HELP_URL_SAMPLE_CHARACTERIZATION;
    toolHeadingNameManage = 'Sample ' + this.sampleName + ' Characterization';

    constructor(  private route: ActivatedRoute, private router: Router ){
    }

    ngOnInit(): void{
    }

}
