import { Component, OnInit } from '@angular/core';
import { TopMainMenuService } from '../../top-main-menu/top-main-menu.service';
import { Consts } from '../../../constants';

@Component( {
    selector: 'canano-workflow',
    templateUrl: './workflow.component.html',
    styleUrls: ['./workflow.component.scss']
} )
export class WorkflowComponent implements OnInit{

    /**
     * These will be provided to canano-main-display-heading as @Input
     */
    helpUrl = Consts.HELP_URL_WORKFLOW;
    toolHeadingName = 'Workflow';

    constructor( private topMainMenuService: TopMainMenuService ){
    }

    ngOnInit(): void{
    }

    onAreaClick( selection ){
        switch( selection ){
            case '#/login':
                this.topMainMenuService.selectMenuItem( 'Home' );
                break;
            case '#/submitProtocol':
                this.topMainMenuService.selectMenuItem( '<PLACE HOLDER>' );
                break;
            default:
                alert( 'Place holder for ' + selection )
                break;
        }
    }

}
