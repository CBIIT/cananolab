import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Consts } from '../../../../../constants';
import { HttpClient } from '@angular/common/http';
import { ApiService } from '../../../../common/services/api.service';
import { Properties } from '../../../../../../assets/properties';
import { NgForm } from '@angular/forms';
import { NanomaterialService } from './nanomaterial.service';

@Component( {
    selector: 'canano-nanomaterialentity',
    templateUrl: './nanomaterialentity.component.html',
    styleUrls: ['./nanomaterialentity.component.scss']
} )
export class NanomaterialentityComponent implements OnInit{
    toolHeadingNameManage = Properties.CURRENT_SAMPLE_NAME + ' Sample Composition - Nanomaterial Entity -';
    helpUrl = Consts.HELP_URL_SAMPLE_COMPOSITION_NANOMATERIAL;
    nanomaterialSampleId;
    nanomaterialDataId;
    editData;
    setupData;
    sampleName;
    description;
    testData = 'XXXXXXX';
    @ViewChild( 'f', { static: false } ) compositionEditForm: NgForm;

    constructor( private router: Router, private route: ActivatedRoute,
                 private httpClient: HttpClient, private apiService: ApiService,
                 private nanomaterialService: NanomaterialService){
    }

    ngOnInit(): void{
        this.route.params.subscribe(
            ( params: Params ) => {
                this.nanomaterialSampleId = params['sampleId'];
                this.nanomaterialService.setNomaterialSampleId(params['sampleId']);
                this.nanomaterialDataId = params['dataId'];
                this.nanomaterialService.setNomaterialDataId(params['dataId']);
                this.sampleName = params['sampleName'];
                this.toolHeadingNameManage = this.sampleName + ' Sample Composition - Nanomaterial Entity';
                console.log( 'MHL nanomaterialSampleId: ', this.nanomaterialSampleId );
                console.log( 'MHL nanomaterialDataId: ', this.nanomaterialDataId );
                // Keep this here, so we don't try to use nanomaterialSampleId before it's set
                this.init();
            }
        );


    }


    init(){

        // Edit data
        this.apiService.doGet( Consts.QUERY_NANOMATERIAL_EDIT, 'sampleId=' + this.nanomaterialSampleId + '&dataId=' + this.nanomaterialDataId ).subscribe(
            data => {
                this.editData = data;
                this.description = data['description'];
                this.nanomaterialService.setNanomaterialEditData( data );
                console.log( 'MHL QUERY_NANOMATERIAL_EDIT: ', data );

            },

            ( err ) => {
                console.error( 'MHL ERROR QUERY_NANOMATERIAL_EDIT: ', err );
            } );


        // setup data
        this.apiService.doGet( Consts.QUERY_NANOMATERIAL_SETUP, 'sampleId=' + this.nanomaterialSampleId ).subscribe(
            data => {
                this.setupData = data;
                this.nanomaterialService.setNanomaterialSetupData( data );
                console.log( 'MHL QUERY_NANOMATERIAL_SETUP: ', data );
            },

            ( err ) => {
                console.error( 'MHL ERROR QUERY_NANOMATERIAL_SETUP: ', err );
            } );

    }

    onSubmit(){

    }
}
