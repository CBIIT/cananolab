import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Consts } from '../../../../../constants';
import { HttpClient } from '@angular/common/http';
import { ApiService } from '../../../../common/services/api.service';
import { Properties } from '../../../../../../assets/properties';
import { NanomaterialService } from './nanomaterial.service';
import { Subject } from 'rxjs';
import { toUpperCase } from 'uri-js/dist/esnext/util';

@Component( {
    selector: 'canano-nanomaterialentity',
    templateUrl: './nanomaterialentity.component.html',
    styleUrls: ['./nanomaterialentity.component.scss']
} )
export class NanomaterialentityComponent implements OnInit, OnDestroy{
    toolHeadingNameManage = Properties.CURRENT_SAMPLE_NAME + ' Sample Composition - Nanomaterial Entity -';
    helpUrl = Consts.HELP_URL_SAMPLE_COMPOSITION_NANOMATERIAL;

    nanomaterialSampleId;
    nanomaterialDataId;
    editData;
    editDataTrailer;
    setupData;
    sampleName;
    description = '';
    descriptionTrailer;
    composingElementsArray;
    testData = 'XXXXXXX';
    newNanomaterial = false;
    newNanomaterialType = '';
    newNanomaterialTypeTrailer = '';
    newNanomaterialTypeText = '';
    selectedOtherSampleNames = [];
    PLEASE_SELECT = '--Select--';

    // The object for all the Type popups save to
    domainEntity = {};
    showTypePopup = new Map();

    selectedBiopolymerType = '';
    selectedBiopolymerTypeTrailer = '';


    // currentDropdownValue is the previous dropdown value selected //
    // This is used to reset the dropdown if other is selected and canceled //
    currentDropdownValues = {};
    // currentField is the current dropdown field on the form //
    // This is used to reset the dropdown if other is selected and canceled //
    currentField;
    otherValue;

    files = [];
    data = {};

    testerData = 'setupData.biopolymerTypes';
    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    // @ViewChild( 'f', { static: false } ) compositionEditForm: NgForm;

    constructor( private router: Router, private route: ActivatedRoute,
                 private httpClient: HttpClient, private apiService: ApiService,
                 private nanomaterialService: NanomaterialService ){
    }

    ngOnInit(): void{
        this.data['fileArray'] = this.files;
        this.route.params.subscribe(
            ( params: Params ) => {
                this.nanomaterialSampleId = params['sampleId'];
                this.nanomaterialService.setNanoMaterialSampleId( params['sampleId'] );
                this.nanomaterialDataId = params['dataId'];
                this.nanomaterialService.setNanoMaterialDataId( params['dataId'] );
                this.sampleName = params['sampleName'];
                this.toolHeadingNameManage = this.sampleName + ' Sample Composition - Nanomaterial Entity';

                // Is it a new one
                this.newNanomaterial = !this.nanomaterialDataId;

                // Keep this here, so we don't try to use nanomaterialSampleId before it's set
                this.init();
            }
        );
    }


    init(){
        // Get Edit data
        if( this.nanomaterialDataId >= 0 ){
            this.apiService.doGet( Consts.QUERY_NANOMATERIAL_EDIT, 'sampleId=' + this.nanomaterialSampleId + '&dataId=' + this.nanomaterialDataId ).subscribe(
                data => {
                    this.editData = data;
                    this.editDataTrailer = { ...this.editData };

                    this.nanomaterialService.setNanomaterialEditData( data );
                    this.composingElementsArray = data['composingElements'];
                    this.nanomaterialService.setComposingElementsArray( this.composingElementsArray ); // @TODO move this to setNanomaterialEditData
                },

                ( err ) => {
                    console.error( 'QUERY_NANOMATERIAL_EDIT: ', err );
                } );
        }else{
            this.editData = this.setDefaultDataSet();
        }

        // setup data
        this.apiService.doGet( Consts.QUERY_NANOMATERIAL_SETUP, 'sampleId=' + this.nanomaterialSampleId ).subscribe(
            data => {
                this.setupData = data;
                this.nanomaterialService.setNanomaterialSetupData( data );
                this.initTypesHash();
            },

            ( err ) => {
                console.error( 'QUERY_NANOMATERIAL_SETUP: ', err );
            } );

    }

    initTypesHash(){
        for( let type of this.setupData['nanomaterialEntityTypes'] ){
            this.showTypePopup.set( type, false );
        }

    }

    // set pointer fields to old values when adding other //
    addOtherValue( field, currentValue ){
        if( currentValue !== 'other' && currentValue !== '[other]' ){
            this.currentDropdownValues[field] = currentValue;
        }
    };




    updatesSelectedOtherSampleNames(){
        if( this.selectedBiopolymerType === '[other]' || this.selectedBiopolymerType === 'other' ){
            this.selectedBiopolymerTypeTrailer = this.selectedBiopolymerType;
        }
    }

    onSubmit(){
    }

    updatesNewNanomaterialType(){
        this.initTypesHash();
        this.showTypePopup.set( this.newNanomaterialType, true );
        this.nanomaterialService.setNewNanomaterialType( this.newNanomaterialType, this.nanomaterialSampleId, this.description );

        this.domainEntity = {};
        this.domainEntity.setValue( 'createdBy', Properties.current_user );
        this.domainEntity.setValue( 'createdDate', Date().valueOf() );
        if( this.newNanomaterialType !== 'other' && this.newNanomaterialType !== '[other]' ){
            this.newNanomaterialTypeTrailer =  this.newNanomaterialType ;
        }


        switch( this.newNanomaterialType ){

            case 'bipolymer':
                this.domainEntity['type'] = '';
                this.domainEntity['sequence'] = '';
                this.domainEntity['name'] = '';
                break;

            case 'carbon nanotube':
                this.domainEntity['averageLength'] = '';
                this.domainEntity['averageLengthUnit'] = '';
                this.domainEntity['chirality'] = '';
                this.domainEntity['diameter'] = '';
                this.domainEntity['diameterUnit'] = '';
                this.domainEntity['wallType'] = '';
                break;

            case 'dendrimer':
                this.domainEntity['branch'] = '';
                this.domainEntity['generation'] = '';
                break;

            case 'emulsion':
                this.domainEntity['polymerName'] = '';
                this.domainEntity['isPolymerized'] = '';
                break;

            case 'fullerene':
                this.domainEntity['numberOfCarbon'] = '';
                this.domainEntity['averageDiameter'] = '';
                this.domainEntity['averageDiameterUnit'] = '';
                break;

            case 'liposome':
                this.domainEntity['polymerName'] = '';
                this.domainEntity['isPolymerized'] = '';
                break;

            case 'metal particle':
                // Nothing for this one
                break;

            case 'Polymer':
                this.domainEntity['isCrossLinked'] = '';
                this.domainEntity['initiator'] = '';
                this.domainEntity['crossLinkDegree'] = '';
                break;

            case 'quantum dot':
                // Nothing for this one
                break;


        }

    }

    setDefaultDataSet(){
        return {
            'sampleId': this.nanomaterialSampleId,
            'composingElements': [],
            'type': this.newNanomaterialType
        };
    };

    resetNanoMaterial(){
        this.init();
    }

    // Save modified nanomaterial
    updateNanoMaterial(){
        let temp = this.editData;
        temp['composingElements'] = this.nanomaterialService.getComposingElementsArray();
        temp['description'] = this.description;
        temp['otherSampleNames'] = this.selectedOtherSampleNames;
        temp['type'] = this.newNanomaterialType;

        if( this.newNanomaterialType === 'emulsion' ){
            if( toUpperCase( this.domainEntity['isPolymerized'] ) === 'YES' ){
                this.domainEntity['isPolymerized'] = true;
            }else{
                this.domainEntity['isPolymerized'] = false;
            }
        }
        temp.setValue( 'domainEntity', this.domainEntity );

        console.log( 'SENDING: ', temp ); // @TESTING
        //  Uncomment to do the save

        /*          this.apiService.doPost( Consts.QUERY_NANOMATERIAL_UPDATE, temp ).subscribe(
                      data => {
                          console.log( 'Return data from QUERY_NANOMATERIAL_UPDATE: ', data );
                      },
                      ( err ) => {
                          console.error( 'ERROR QUERY_NANOMATERIAL_UPDATE: ', err );
                      } );
          */
    }


    saveOther( newItem: Object ){
        if( newItem['change'] && newItem['value'] ){
            // Add to dropdown array
            this.addDropdownItem( newItem['array'], newItem['value'] );

            if( newItem['value'] !== 'other' && newItem['value'] !== '[other]' ){
                // Set menu's bound value
                this.domainEntity[newItem['field']] = newItem['value'];
                this.currentDropdownValues[newItem['field']] = this.domainEntity[newItem['field']];
            }

        }else{
            this.setValue( this.domainEntity[newItem['field']], this.currentDropdownValues[newItem['field']] );
            this.domainEntity[newItem['field']] = this.currentDropdownValues[newItem['field']];
        }
    };

    changeType( event, field ){
        if( this.domainEntity[field] !== 'other' && this.domainEntity[field] !== '[other]' ){
            this.currentDropdownValues[field] = this.domainEntity[field];
        }
    }


    changeFile( newItem: Object ){
        if( newItem['type'] === 'save' || newItem['type'] === 'delete' ){
            console.log('changeFile: ', newItem );
        }
    }

    onDeleteNanoMaterial(){
        this.apiService.doPost( Consts.QUERY_NANOMATERIAL_DELETE, this.editData ).subscribe(
            data => {
                console.log( 'Return data from QUERY_NANOMATERIAL_DELETE: ', data );
            },
            ( err ) => {
                console.error( 'ERROR QUERY_NANOMATERIAL_DELETE: ', err );
            } );

    }

    addNewNanomaterialEntityType(){
        this.setupData['nanomaterialEntityTypes'].push( this.newNanomaterialTypeText );
        this.newNanomaterialType = this.newNanomaterialTypeText;
    }

    cancelNewNanomaterialEntityType(){
        if( this.newNanomaterialTypeTrailer !== 'other' && this.newNanomaterialTypeTrailer !== '[other]' ){
            this.newNanomaterialType = this.newNanomaterialTypeTrailer;
        }
    }

    ngOnDestroy(): void{
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }
}
