import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';
import { NavigationService } from '../../../../common/services/navigation.service';
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
    compositionData;

    constructor( private navigationService:NavigationService,private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
    }

    ngOnInit(): void{
        this.navigationService.setCurrentSelectedItem(1);
        this.route.params.subscribe(
            ( params: Params ) => {
                this.sampleId = params['sampleId'].replace( /^.*\?sampleId=/, '' );
                if(
                    this.sampleId <= 0 ){
                    this.sampleId = Properties.CURRENT_SAMPLE_ID;
                }else{
                    Properties.CURRENT_SAMPLE_ID = this.sampleId;
                };
                this.compositionData = this.getCompositionEditData().subscribe(
                    data => {
                        console.log(data)
                        Properties.SAMPLE_TOOLS = true;
                        this.compositionData = data;
                        Properties.CURRENT_SAMPLE_NAME = data['sampleName'];
                        console.log('MHL Properties.CURRENT_SAMPLE_NAME: ', Properties.CURRENT_SAMPLE_NAME );
                        console.log('MHL getCompositionEditData: ', this.getCompositionEditData() );
                    } );
            }
        );
    }

    getCompositionEditData(){
        let getUrl = Properties.API_SERVER_URL + '/caNanoLab/rest/composition/summaryView?sampleId=' + this.sampleId;

        if( Properties.DEBUG_CURL ){
            let curl = 'curl  -k \'' + getUrl + '\'';
            console.log( curl );
        }

        let headers = new HttpHeaders( {
            'Content-Type': 'application/x-www-form-urlencoded'
        } );

        let options = {
            headers: headers,
            method: 'get',
        };

        let results;
        try{
            results = this.httpClient.get( getUrl, options ).pipe( timeout( Properties.HTTP_TIMEOUT ) );
        }catch( e ){
            // TODO react to error.
            console.error( 'doGet Exception: ' + e );
        }
        return results;

    }
    onChemicalAssociationClick(dataId){
        if (dataId==-1){
            this.router.navigate( ['home/samples/composition/chemicalassociation', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
        else {
            this.router.navigate( ['home/samples/composition/chemicalassociation', Properties.CURRENT_SAMPLE_ID, dataId] );  // @FIXME  Don't hard code these
        }
    }
    onFunctionalizingEntityClick(dataId){
        if (dataId==-1){
            this.router.navigate( ['home/samples/composition/functionalizingentity', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
        else {
            this.router.navigate( ['home/samples/composition/functionalizingentity', Properties.CURRENT_SAMPLE_ID, dataId] );  // @FIXME  Don't hard code these
        }
    }

    onNanomaterialEntityClick(dataId){
        console.log('MHL onNanomaterialEntityClick: ', dataId);
        console.log('MHL onNanomaterialEntityClick this.sampleName: ', this.sampleName);
        this.router.navigate( ['home/samples/composition/nanomaterialentity', Properties.CURRENT_SAMPLE_ID, dataId, this.sampleName] );  // @FIXME  Don't hard code these
    }
    onCompositionFileClick(dataId){
        if (dataId==-1){
            this.router.navigate( ['home/samples/composition/compositionfile', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
        else {
            this.router.navigate( ['home/samples/composition/compositionfile', Properties.CURRENT_SAMPLE_ID, dataId] );  // @FIXME  Don't hard code these
        }
    }


}
