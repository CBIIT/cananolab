import { Component, OnInit } from '@angular/core';
import { SampleAvailabilityDisplayService } from './sample-availability-display.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Consts } from '../../../../../../../constants';
import { ApiService } from '../../../../../../common/services/api.service';

@Component( {
    selector: 'canano-sample-availability-display',
    templateUrl: './sample-availability-display.component.html',
    styleUrls: ['./sample-availability-display.component.scss']
} )
export class SampleAvailabilityDisplayComponent implements OnInit{
    showSampleAvailabilityDisplay = false;
    htmlData = '';
    sampleData = ''; // @TODO This will replace htmlData

    heading = 'Heading place holder';

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private sampleAvailabilityDisplayService: SampleAvailabilityDisplayService, private apiService: ApiService  ){
    }

    ngOnInit(): void{
        this.sampleAvailabilityDisplayService.showSampleAvailabilityDisplayEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            ( data ) => {
                this.sampleData = data;
                this.heading = 'caNanoLab Availability Score: ' + this.sampleData['caNanoLabScore'] + '  MINChar Availability Score: ' + this.sampleData['mincharScore'];
                this.showSampleAvailabilityDisplay = true;
            },
            err => {
                console.error( 'sampleAvailabilityDisplayService: ', err );
            } );

    }

    onCloseSampleAvailabilityDisplay(){
        this.showSampleAvailabilityDisplay = false;
    }

    getPopupHtml(){
        // QUERY_SAMPLE_AVAILABILITY_HTML

        this.apiService.doGet( Consts.QUERY_SAMPLE_AVAILABILITY_HTML, '' ).subscribe(
            data => {
                this.htmlData = data;
                this.htmlData = this.htmlData.replace( /images\//g, 'assets/images/');
            } );
    }
}
