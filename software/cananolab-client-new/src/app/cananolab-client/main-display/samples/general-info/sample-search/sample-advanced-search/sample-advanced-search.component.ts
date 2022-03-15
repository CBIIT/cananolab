import { Component, OnInit } from '@angular/core';
import { Consts } from '../../../../../../constants';
import { ApiService } from '../../../../../common/services/api.service';

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
    PLEASE_SELECT = '-- Please Select --';

    sampleQueries = [];
    compositionQueries = [];

    sampleCriteriaTextInput = 'XXX';
    compositionCriteriaTextInput = 'ZZZ';

    sampleCriteriaEditOperand;
    sampleCriteriaOperand;
    compositionEditCriteriaOperand;
    compositionCriteriaOperand;

    sampleCriteriaNameType;
    sampleCriteriaEditNameType;

    compositionCriteriaCompositionType;
    compositionEditCriteriaCompositionType;
    characterizationCriteriaDropdownOneValue;
    characterizationCriteriaDropdownTwo;
    characterizationCriteriaDropdownThree;
    characterizationCriteriaDropdownThreeValue;

    characterizationCriteriaDropdownTwoValue;

    compositionCriteriaDropdownTwoValue;
    compositionEditCriteriaDropdownTwoValue;

    sampleLogicalOperator = 'and'
    compositionLogicalOperator = 'and';

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

    constructor( private apiService: ApiService ){
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
        this.apiService.doGet( Consts.QUERY_SAMPLE_ADVANCED_SEARCH_SETUP, '' ).subscribe(
            data => {
                this.sampleData = data;
            },
            ( err ) => {
                console.log( 'ERROR QUERY_SAMPLE_AVAILABILITY: ', err );
            } );
    }

    // Composition add 2nd dropdown
    onCompositionCriteriaDropdownTwo(){
        console.log( 'MHL onCompositionCriteriaDropdownTwo: ', this.onCompositionEditCriteriaDropdownTwo );
    }

    // Composition edit 2nd dropdown
    onCompositionEditCriteriaDropdownTwo(){
        console.log( 'MHL onCompositionEditCriteriaDropdownTwo: ', this.compositionCriteriaDropdownTwo );
        console.log( 'MHL compositionEditCriteriaDropdownTwoValue: ', this.compositionEditCriteriaDropdownTwoValue );
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
                alert( 'Place holder for ' + this.compositionCriteriaCompositionType )
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
                alert( 'Place holder for ' + this.compositionEditCriteriaCompositionType )
                console.log( 'MHL ERROR onCompositionEditCriteriaDropdownOne sampleData: ', this.sampleData );
                break;
        }
        this.compositionEditCriteriaDropdownTwoValue = this.PLEASE_SELECT;
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
    compositionCriteriaAndRadioClick(){
        this.compositionLogicalOperator = 'and';    }

    compositionCriteriaOrRadioClick(){
        this.compositionLogicalOperator = 'or';
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


    onAddSampleCriteria(){
        this.sampleQueries.push( {
            'nameType': this.sampleCriteriaNameType,
            'operand': this.sampleCriteriaOperand,
            'type': 'SampleQueryBeen',
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
        console.log( 'MHL onResetCompositionEditCriteria' );
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

        // (re)Setup Add criteria update
        this.compositionEditCriteriaOperand = this.PLEASE_SELECT;
        this.compositionEditCriteriaDropdownTwoValue = this.PLEASE_SELECT;
        this.compositionCriteriaDropdownTwoValue = this.PLEASE_SELECT;
        this.characterizationCriteriaDropdownThreeValue = this.PLEASE_SELECT;
        this.editCompositionQueryMode = false;
    }

    onCharacterizationCriteriaDropdownOne(){
        this.getCharacterizationOptions( this.getValueByLabel( this.sampleData['characterizationTypes'], this.characterizationCriteriaDropdownOneValue) );
    }

    onCharacterizationCriteriaDropdownTwo(){
        console.log('MHL characterizationCriteriaDropdownTwoValue: ', this.characterizationCriteriaDropdownTwoValue);
        this.getDatumOptions( this.characterizationCriteriaDropdownOneValue,  this.getValueByLabel( this.characterizationCriteriaDropdownTwo,  this.characterizationCriteriaDropdownTwoValue ));
    }

    onCharacterizationCriteriaDropdownThree(){
        console.log('MHL onCharacterizationCriteriaDropdownThree: ', this.characterizationCriteriaDropdownThreeValue );
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
    getDatumOptions( charType, charName ){
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
     * Each element has a "value" and a "label", "label" is for display in menus, "value" is used in Server calls.
     * @param array
     * @param label
     */
    getValueByLabel( array, label){
        for( let f = 0; f < array.length; f++){
            if( array[f]['label'] === label){
                return array[f]['value'];
            }
        }
        // Not found  @TODO react to this error
        console.error('Could not find ' + label + ' in array.');
        return '';
    }

}
