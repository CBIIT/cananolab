import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';
import { NavigationService } from '../../../../common/services/navigation.service';
import { ApiService } from '../../../../common/services/api.service';
import {StatusDisplayService } from '../../../../status-display/status-display.service';
@Component( {
    selector: 'canano-composition',
    templateUrl: './composition.component.html',
    styleUrls: ['./composition.component.scss']
} )
export class CompositionComponent implements OnInit{
    sampleId = Properties.CURRENT_SAMPLE_ID;
    sampleName = Properties.CURRENT_SAMPLE_NAME;
    helpUrl =  Consts.HELP_URL_SAMPLE_COMPOSITION;
    toolHeadingNameManage;
    data;
    editUrl=false;
    sectionToShow='all';
    constructor( private statusDisplayService:StatusDisplayService,private apiService:ApiService,private navigationService:NavigationService,private router: Router, private route: ActivatedRoute ){
    }

    ngOnInit(): void{
        this.editUrl=this.statusDisplayService.isEditUrl();
        this.navigationService.setCurrentSelectedItem(1);
        this.route.params.subscribe(
            ( params: Params ) => {
                setTimeout(()=> {
                    Properties.SAMPLE_TOOLS = true;

                },200)
                this.sampleId = params['sampleId'];
                if(
                    this.sampleId <= 0 ){
                    this.sampleId = Properties.CURRENT_SAMPLE_ID;
                }else{
                    Properties.CURRENT_SAMPLE_ID = this.sampleId;
                };
                this.apiService.getSampleName(this.sampleId).subscribe(
                    data=>this.toolHeadingNameManage='Sample ' +data['sampleName'] + ' Composition'
                )
                this.data = this.getCompositionEditData().subscribe(
                    data => {
                        this.data = data;
                        Properties.CURRENT_SAMPLE_NAME = data['sampleName'];
                    } );
            }
        );
    }

    convertTitlesToRealWords(title) {
        const wordRegex = /[A-Z]?[a-z]+|[0-9]+|[A-Z]+(?![a-z])/g;
        const result = title.match(wordRegex);
        return result.join(' ')
    }

    getCompositionEditData(){
        let getUrl = Consts.QUERY_COMPOSITION_SUMMARY_VIEW;

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
            results = this.apiService.doGet(getUrl,'sampleId=' + this.sampleId).pipe( timeout( Properties.HTTP_TIMEOUT ) );
        }catch( e ){
            // TODO react to error.
            console.error( 'doGet Exception: ' + e );
        }
        return results;

    }
    onChemicalAssociationClick(dataId){
        if (dataId==-1){
            this.router.navigate( ['home/samples/composition/chemical-association', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
        else {
            this.router.navigate( ['home/samples/composition/chemical-association', Properties.CURRENT_SAMPLE_ID, dataId] );  // @FIXME  Don't hard code these
        }
    }
    onFunctionalizingEntityClick(dataId){
        if (dataId==-1){
            this.router.navigate( ['home/samples/composition/functionalizing-entity', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
        else {
            this.router.navigate( ['home/samples/composition/functionalizing-entity', Properties.CURRENT_SAMPLE_ID, dataId] );  // @FIXME  Don't hard code these
        }
    }

    onNanomaterialEntityClick(dataId){
        if (dataId==-1) {
            this.router.navigate( ['home/samples/composition/nanomaterial-entity', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
        else {
            this.router.navigate( ['home/samples/composition/nanomaterial-entity', Properties.CURRENT_SAMPLE_ID, dataId] );  // @FIXME  Don't hard code these
        }
    }
    onCompositionFileClick(dataId){
        if (dataId==-1){
            this.router.navigate( ['home/samples/composition/composition-file', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
        }
        else {
            this.router.navigate( ['home/samples/composition/composition-file', Properties.CURRENT_SAMPLE_ID, dataId] );  // @FIXME  Don't hard code these
        }
    }

    propertyCheck(entry) {
        if (entry['Properties']) {
            let keys=Object.keys(entry['Properties']);
            let displayableItems=[];
            keys.forEach(item=> {
                if (entry.Properties[item]&&entry.Properties[item]!='') {
                    displayableItems.push(item);
                };
            });
            if (displayableItems.length) {
                return true
            };
        }


        return false
    }

    showSection(value) {
        this.sectionToShow=value;
    }

    splitKeywordString(keyword) {
        return keyword.split('\n')
    }


}
