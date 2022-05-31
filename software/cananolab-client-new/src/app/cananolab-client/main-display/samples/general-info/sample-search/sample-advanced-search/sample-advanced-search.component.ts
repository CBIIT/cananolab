import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../../../constants';
import { ApiService } from '../../../../../common/services/api.service';
import { Router } from '@angular/router';
import { SampleAdvancedSearchService } from './sample-advanced-search.service';

@Component( {
    selector: 'canano-sample-advanced-search',
    templateUrl: './sample-advanced-search.component.html',
    styleUrls: ['./sample-advanced-search.component.scss']
} )
export class SampleAdvancedSearchComponent implements OnInit{
    toolHeadingAdvancedSearchSample = 'Advanced Sample Search';
    helpUrl = Consts.HELP_URL_SAMPLE_ADVANCED_SEARCH;
    AND = 0;
    OR = 1;
    PLEASE_SELECT = '--Select--';
    masterLogicalOperator = 'and';

    sampleQueries = [];
    compositionQueries = [];
    characterizationQueries = [];

    sampleCriteriaTextInput = '';
    compositionCriteriaTextInput = '';

    sampleCriteriaEditOperand;
    sampleCriteriaOperand;
    compositionEditCriteriaOperand;
    compositionCriteriaOperand;

    sampleCriteriaNameType;
    sampleCriteriaEditNameType;
    compositionCriteriaCompositionType;
    compositionEditCriteriaCompositionType;

    // characterization
    characterizationCriteriaDropdownTwo;
    characterizationCriteriaDropdownThree;
    characterizationCriteriaDropdownFour = [];
    characterizationCriteriaDropdownOneValue = this.PLEASE_SELECT;
    characterizationCriteriaDropdownThreeValue = this.PLEASE_SELECT;
    characterizationCriteriaDropdownFourValue = this.PLEASE_SELECT;
    characterizationCriteriaDropdownTwoValue = this.PLEASE_SELECT;
    characterizationDatumValue = '';
    characterizationDatumName = ''; // @CHECKME this is going to have the same value as characterizationCriteriaDropdownThreeValue?
    characterizationOperandSelect = this.PLEASE_SELECT;

    compositionCriteriaDropdownTwoValue;
    compositionEditCriteriaDropdownTwoValue;

    sampleLogicalOperator = 'and'
    compositionLogicalOperator = 'and';
    characterizationLogicalOperator = 'and';

    editSampleQueryMode = false;
    editCompositionQueryMode = false;
    editCharacterizationQueryMode = false;

    sampleEditCriteriaTextInput;
    compositionEditCriteriaTextInput;
    currentSampleEditCriteriaEditIndex = -1;
    currentCompositionEditCriteriaEditIndex = -1;
    compositionCriteriaDropdownTwo;
    compositionEditCriteriaDropdownTwo;

    sampleData;

    constructor( private apiService: ApiService, private router: Router,
                 private sampleAdvancedSearchService: SampleAdvancedSearchService ){
    }

    ngOnInit(): void{
        this.sampleCriteriaNameType = this.PLEASE_SELECT;
        this.sampleCriteriaOperand = this.PLEASE_SELECT;

        this.compositionEditCriteriaCompositionType = this.PLEASE_SELECT;
        this.compositionCriteriaCompositionType = this.PLEASE_SELECT;
        this.compositionCriteriaOperand = this.PLEASE_SELECT;
        this.compositionCriteriaDropdownTwoValue = this.PLEASE_SELECT;

        this.characterizationCriteriaDropdownOneValue = this.PLEASE_SELECT;
        this.init();
    }


    init(){

        // Get initial values to set up UI.
        // For Characterization we will need make server calls as the menus are populated, so not much setup data for Characterization
        this.apiService.doGet( Consts.QUERY_SAMPLE_ADVANCED_SEARCH_SETUP, '' ).subscribe(
            data => {
                this.sampleData = data;
            },
            ( err ) => {
                console.log( 'ERROR QUERY_SAMPLE_ADVANCED_SEARCH_SETUP: ', err );
            } );
    }

    // Composition add 2nd dropdown
    onCompositionCriteriaDropdownTwo(){
        this.compositionCriteriaOperand = this.PLEASE_SELECT;
    }

    // Composition edit 2nd dropdown
    onCompositionEditCriteriaDropdownTwo(){
        // console.log( 'onCompositionEditCriteriaDropdownTwo: ', this.compositionCriteriaDropdownTwo );
        // console.log( 'compositionEditCriteriaDropdownTwoValue: ', this.compositionEditCriteriaDropdownTwoValue );
    }

    onResetCompositionCriteria(){
        this.compositionQueries = [];
        this.currentCompositionEditCriteriaEditIndex = -1;
        this.clearInputUpdateCompositionCriteria();
    }


    /**
     * When the first composition dropdown selected value changes.
     */
    onCompositionCriteriaDropdownOne(){

        switch( this.compositionCriteriaCompositionType ){
            case 'nanomaterial entity':
                this.compositionCriteriaDropdownTwo = this.sampleData['nanomaterialEntityTypes'];
                break;

            case 'functionalizing entity':
                this.compositionCriteriaDropdownTwo = this.sampleData['functionalizingEntityTypes'];
                break;

            case 'function':
                this.compositionCriteriaDropdownTwo = this.sampleData['functionTypes'];
                break;

            default:
                break;
        }
        this.compositionCriteriaDropdownTwoValue = this.PLEASE_SELECT;

    }

    /**
     * Updates 2nd dropdown when selection is made in 1st dropdown
     */
    onCompositionEditCriteriaDropdownOne(){

        switch( this.compositionEditCriteriaCompositionType ){
            case 'nanomaterial entity':
                this.compositionEditCriteriaDropdownTwo = this.sampleData['nanomaterialEntityTypes'];
                break;

            case 'functionalizing entity':
                this.compositionEditCriteriaDropdownTwo = this.sampleData['functionalizingEntityTypes'];
                break;

            case 'function':
                this.compositionEditCriteriaDropdownTwo = this.sampleData['functionTypes'];
                break;

            default:
                console.log( 'ERROR Unknown onCompositionEditCriteriaDropdownOne Type sampleData: ', this.sampleData );
                break;
        }
        this.compositionEditCriteriaDropdownTwoValue = this.PLEASE_SELECT;
    }


    compositionCriteriaAndRadioClick(){
        this.compositionLogicalOperator = 'and';
    }

    compositionCriteriaOrRadioClick(){
        this.compositionLogicalOperator = 'or';
    }


    onEditSampleCriteria( i ){
        this.currentSampleEditCriteriaEditIndex = i;
        // Initialization
        this.sampleCriteriaEditNameType = this.sampleQueries[i]['nameType'];
        this.sampleCriteriaEditOperand = this.sampleQueries[i]['operand'];
        this.sampleEditCriteriaTextInput = this.sampleQueries[i]['name'];
        this.editSampleQueryMode = true;
    }

    onEditCompositionCriteria( i ){
        this.compositionCriteriaDropdownTwoValue = this.compositionQueries[i]['entityType'];
        this.editCompositionQueryMode = true;
        this.compositionEditCriteriaCompositionType = this.compositionQueries[i]['composition'];
        this.onCompositionEditCriteriaDropdownOne();  // To update 2nd dropdown
        this.compositionEditCriteriaDropdownTwoValue = this.compositionQueries[i]['entityType'];
        this.currentCompositionEditCriteriaEditIndex = i;
        if( this.compositionCriteriaCompositionType !== 'function' ){
            this.compositionEditCriteriaOperand = this.compositionQueries[i]['operand'];
            this.compositionEditCriteriaTextInput = this.compositionQueries[i]['chemicalName'];
        }

    }

    characterizationCriteriaAndRadioClick(){
        this.characterizationLogicalOperator = 'and';
    }

    characterizationCriteriaOrRadioClick(){
        this.characterizationLogicalOperator = 'or';
    }

    sampleCriteriaAndRadioClick(){
        this.sampleLogicalOperator = 'and';
    }

    sampleCriteriaOrRadioClick(){
        this.sampleLogicalOperator = 'or';
    }

    onResetSampleCriteria(){
        this.sampleQueries = [];
        this.currentSampleEditCriteriaEditIndex = -1;
        this.clearInputUpdateSampleCriteria();
    }

    clearInputUpdateSampleCriteria(){
        this.sampleCriteriaTextInput = '';
        this.sampleCriteriaNameType = this.PLEASE_SELECT;
        this.sampleCriteriaOperand = this.PLEASE_SELECT;
        this.editSampleQueryMode = false;
    }

    clearInputUpdateCompositionCriteria(){
        this.compositionCriteriaCompositionType = this.PLEASE_SELECT;
        this.compositionCriteriaDropdownTwoValue = this.PLEASE_SELECT;
        this.compositionCriteriaOperand = this.PLEASE_SELECT;
        this.compositionCriteriaTextInput = '';
    }

    clearInputUpdateCharacterizationCriteria(){
        this.characterizationCriteriaDropdownOneValue = this.PLEASE_SELECT;
        this.characterizationCriteriaDropdownTwoValue = this.PLEASE_SELECT;
        this.characterizationCriteriaDropdownThreeValue = this.PLEASE_SELECT;
        this.characterizationLogicalOperator = 'and';
        this.characterizationDatumValue = '';

    }

    onAddSampleCriteria(){
        this.sampleQueries.push( {
            'nameType': this.sampleCriteriaNameType,
            'operand': this.sampleCriteriaOperand,
            'type': 'SampleQueryBean',
            'name': this.sampleCriteriaTextInput
        } );

        // Return UI to initial values
        this.clearInputUpdateSampleCriteria();
    }

    onAddCompositionCriteria(){
        let temp = {};
        temp['composition'] = this.compositionCriteriaCompositionType; // compositionCriteriaCompositionType !== 'function'
        temp['entityType'] = this.compositionCriteriaDropdownTwoValue;
        temp['type'] = 'CompositionQueryBeen';
        if( this.compositionCriteriaCompositionType !== 'function' ){
            temp['operand'] = this.compositionCriteriaOperand;
            temp['chemicalName'] = this.compositionCriteriaTextInput;
        }
        this.compositionQueries.push( temp );

        // Return UI to initial values
        this.clearInputUpdateCompositionCriteria();
    }


    onUpdateSampleCriteria(){
        this.sampleQueries[this.currentSampleEditCriteriaEditIndex]['nameType'] = this.sampleCriteriaEditNameType;
        this.sampleQueries[this.currentSampleEditCriteriaEditIndex]['operand'] = this.sampleCriteriaEditOperand;
        this.sampleQueries[this.currentSampleEditCriteriaEditIndex]['name'] = this.sampleEditCriteriaTextInput;
        this.sampleCriteriaEditOperand = this.PLEASE_SELECT;

        this.editSampleQueryMode = false;
    }

    onResetCompositionEditCriteria(){
        this.compositionQueries = [];
        this.clearInputUpdateCompositionCriteria();
    }

    onDeleteSampleCriteria(){
        this.sampleQueries.splice( this.currentSampleEditCriteriaEditIndex, 1 );
        this.editSampleQueryMode = false;
    }

    onUpdateCompositionEditCriteria(){
        this.compositionQueries[this.currentCompositionEditCriteriaEditIndex]['chemicalName'] = this.compositionEditCriteriaTextInput;
        this.compositionQueries[this.currentCompositionEditCriteriaEditIndex]['composition'] = this.compositionEditCriteriaCompositionType;
        this.compositionQueries[this.currentCompositionEditCriteriaEditIndex]['entityType'] = this.compositionEditCriteriaDropdownTwoValue;
        this.compositionQueries[this.currentCompositionEditCriteriaEditIndex]['operand'] = this.compositionEditCriteriaOperand;

        this.clearInputUpdateCharacterizationCriteria();
        this.editCompositionQueryMode = false;
    }

    onCharacterizationCriteriaDropdownOne(){
        this.getCharacterizationOptions( this.getValueByLabel( this.sampleData['characterizationTypes'], this.characterizationCriteriaDropdownOneValue ) );
    }

    onCharacterizationCriteriaDropdownTwo(){
        this.getCharacterizationDatumOptions( this.characterizationCriteriaDropdownOneValue, this.getValueByLabel( this.characterizationCriteriaDropdownTwo, this.characterizationCriteriaDropdownTwoValue ) );
    }

    onCharacterizationCriteriaDropdownThree(){
        this.characterizationDatumName = this.characterizationCriteriaDropdownThreeValue;
        this.getCharacterizationDatumUnitOptions( this.characterizationDatumName );
    }

    // @TODO we shouldn't need this
    onCharacterizationCriteriaDropdownFour(){
        // console.log( 'MHL onCharacterizationCriteriaDropdownFour' );
    }

    onDeleteCompositionCriteria(){
        this.compositionQueries.splice( this.currentCompositionEditCriteriaEditIndex, 1 );
        this.editCompositionQueryMode = false;
    }

    /**
     * Get values for 2nd menu using selection from 1st menu
     * @param charType
     */
    getCharacterizationOptions( charType ){
        this.apiService.doGet( Consts.QUERY_SAMPLE_GET_CHARACTERIZATION_OPTIONS, 'charType=' + charType ).subscribe(
            data => {
                this.characterizationCriteriaDropdownTwo = data;
                this.characterizationCriteriaDropdownTwoValue = this.PLEASE_SELECT;
            },
            ( err ) => {
                console.log( 'ERROR QUERY_SAMPLE_GET_CHARACTERIZATION_OPTIONS: ', err );
            } );
    }

    /**
     * Get values for 3rd menu using selection from 2nd menu
     */
    getCharacterizationDatumOptions( charType, charName ){
        this.apiService.doGet( Consts.QUERY_SAMPLE_GET_DATUM_OPTIONS, 'charType=' + charType + '&charName=' + charName ).subscribe(
            data => {
                this.characterizationCriteriaDropdownThree = data;
                this.characterizationCriteriaDropdownThreeValue = this.PLEASE_SELECT;
            },
            ( err ) => {
                console.log( 'ERROR QUERY_SAMPLE_GET_DATUM_OPTIONS: ', err );
            } );
    }


    /**
     * Get values for 4rd menu using selection from 3nd menu
     */
    getCharacterizationDatumUnitOptions( datumName ){
        this.apiService.doGet( Consts.QUERY_SAMPLE_GET_DATUM_UNIT_OPTIONS, 'datumName=' + datumName ).subscribe(
            data => {
                if( data.length < 1 ){
                    this.characterizationCriteriaDropdownFour = [];
                }else{
                    this.characterizationCriteriaDropdownFour = String( data ).split( ',' );
                }
                this.characterizationCriteriaDropdownFourValue = this.PLEASE_SELECT;
            },
            ( err ) => {
                console.log( 'ERROR QUERY_SAMPLE_GET_DATUM_UNIT_OPTIONS: ', err );
            } );
    }

    /**
     * Each element has a "value" and a "label", "label" is for display in menus, "value" is used in Server calls.
     * @param array
     * @param label
     */
    getValueByLabel( array, label ){
        for( let f = 0; f < array.length; f++ ){
            if( array[f]['label'] === label ){
                return array[f]['value'];
            }
        }
        // Not found  @TODO react to this error
        console.error( 'Could not find ' + label + ' in array.' );
        return '';
    }

    onAddCharacterizationCriteria(){
        let tempObj = {
            'characterizationType': this.characterizationCriteriaDropdownOneValue,
            'characterizationNameLabel': this.characterizationCriteriaDropdownTwoValue,  // this.characterizationCriteriaDropdownTwoValue,
            'characterizationName': this.getValueByLabel( this.characterizationCriteriaDropdownTwo, this.characterizationCriteriaDropdownTwoValue ),  // this.characterizationCriteriaDropdownTwoValue,
            'datumName': this.characterizationCriteriaDropdownThreeValue,
            'datumValue': this.characterizationDatumValue,
            'operand': this.characterizationOperandSelect,
            'type': 'CharacterizationQueryBean'
        };
        if( this.characterizationCriteriaDropdownFourValue === this.PLEASE_SELECT ){
            tempObj['datumValueUnit'] = '';
        }else{
            tempObj['datumValueUnit'] = this.characterizationCriteriaDropdownFourValue;
        }

        this.characterizationQueries.push( tempObj );

        // Return Characterization part of UI to initial values
        this.clearInputUpdateCharacterizationCriteria();
    }


    onEditCharacterizationCriteria( i ){
        console.log( 'onEditCharacterizationCriteria: ', i );
    }

    masterCriteriaAndRadioClick(){
        this.masterLogicalOperator = 'and';
    }

    masterCriteriaOrRadioClick(){
        this.masterLogicalOperator = 'or';
    }

    onResetCharacterizationCriteria(){
        this.characterizationQueries = [];
        this.clearInputUpdateCharacterizationCriteria();
    }

    clearCharacterizationUi(){
        this.characterizationCriteriaDropdownOneValue = this.PLEASE_SELECT;
        this.characterizationCriteriaDropdownTwoValue = this.PLEASE_SELECT;
        this.characterizationCriteriaDropdownThreeValue = this.PLEASE_SELECT;
    }

    cleanCharacterizationQueries(){
        for( let i = 0; i < this.characterizationQueries.length; i++ ){
            this.characterizationQueries[i]['characterizationNameLabel'] = undefined;
            console.log( this.characterizationQueries[i] );
        }
    }

    onSearchClicked(){
        this.cleanCharacterizationQueries();

        let queryObject = {};
        queryObject['sampleQueries'] = this.sampleQueries;
        queryObject['compositionQueries'] = this.compositionQueries;
        queryObject['characterizationQueries'] = this.characterizationQueries;
        queryObject['sampleLogicalOperator'] = this.sampleLogicalOperator;
        queryObject['compositionLogicalOperator'] = this.compositionLogicalOperator;
        queryObject['characterizationLogicalOperator'] = this.characterizationLogicalOperator;
        queryObject['logicalOperator'] = this.masterLogicalOperator;

        this.apiService.doPost( Consts.QUERY_SAMPLE_ADVANCED_SEARCH, queryObject ).subscribe(
            data => {
                this.sampleAdvancedSearchService.setSearchResults( data['resultTable']['dataRows'] );
                this.router.navigate( ['home/samples/sample-advanced-search-results'] );

            },
            ( err ) => {
                console.log( 'ERROR QUERY_SAMPLE_ADVANCED_SEARCH err: ', err );
            } );

    }

    onResetClicked(){
        this.clearInputUpdateSampleCriteria();
        this.clearInputUpdateCompositionCriteria();
        this.clearInputUpdateCharacterizationCriteria();
        this.sampleQueries = [];
        this.compositionQueries = [];
        this.characterizationQueries = [];
    }
}
