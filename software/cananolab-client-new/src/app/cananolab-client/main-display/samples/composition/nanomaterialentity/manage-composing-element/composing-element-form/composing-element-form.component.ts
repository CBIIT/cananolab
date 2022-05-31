import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ApiService } from '../../../../../../common/services/api.service';
import { NanomaterialService } from '../../nanomaterial.service';
import { UtilService } from '../../../../../../common/services/util.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Consts } from '../../../../../../../constants';

@Component( {
    selector: 'canano-composing-element-form',
    templateUrl: './composing-element-form.component.html',
    styleUrls: ['./composing-element-form.component.scss']
} )
export class ComposingElementFormComponent implements OnInit, OnDestroy{
    @Input() composingElementsArray;

    // This will (later) be wired to a button in manage-composing-element
    showAddComposingElement = false;
    showEditComposingElement = false;
    editMode = false;

    typeTrailer = '';
    amountUnitTrailer = '';
    molecularFormulaTypeTrailer;
    selectedInherentFunctionTypeTrailer;
    showAddInherentFunction;
    newNanomaterialType = '';
    sampleId = '';
    description = '';

    editData;
    setupData;
    elementName = '';

    otherTypeText;
    localFormOtherAmountUnitText;
    otherMolecularFormulaType;
    selectedInherentFunctionType;
    otherFunctionTypeText;

    currentEditComposingElement = {
        type: '',
        name: '',
        pubChemSourceName: '',
        pubChemId: '',
        value: '',
        valueUnit: '',
        molecularFormulaType: '',
        molecularFormula: '',
        description: ''
    }

    tempHold;
    currentEditIndex = -1;
    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private apiService: ApiService, private nanomaterialService: NanomaterialService,
                 private utilService: UtilService ){
    }

    ngOnInit(): void{

        // Edit
        this.nanomaterialService.editComposingElementShowEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            ( data ) => {
                this.editMode = true;
                this.showEditComposingElement = data;
            }
        );


        this.nanomaterialService.newComposingElementShowEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            ( data ) => {
                this.editMode = false;
                this.showAddComposingElement = data;

            }
        );


        this.nanomaterialService.editComposingElementIndexEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            ( data ) => {
                this.currentEditIndex = data;
                this.currentEditComposingElement = this.composingElementsArray[this.currentEditIndex];
                this.initTrailer();
            }
        );


        // New Nanomaterial Type
        this.nanomaterialService.newNanomaterialTypeEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            ( data ) => {
                this.newNanomaterialType = data['type'];
                this.sampleId = data['sampleId'];
                this.description = data['nanomaterialDescription'];
            }
        );


        this.init();
    }

    async init(){
        this.editData = undefined;
        this.setupData = undefined;
        // @FIXME needs a backstop!
        while( this.editData === undefined || this.setupData === undefined ){
            this.editData = this.nanomaterialService.getNanomaterialEditData();
            this.setupData = this.nanomaterialService.getNanomaterialSetupData();
            await this.utilService.sleep( 250 );
        }
        this.composingElementsArray = this.editData['composingElements'];
    }

    async initTrailer(){
        let runaway = 100;
        while( this.composingElementsArray === undefined && runaway > 0 ){
            await this.utilService.sleep( 500 );
            runaway--;
        }
        this.tempHold = { ...this.composingElementsArray[this.currentEditIndex] };
    }


    // @TODO remove this won't't be needed
    getAddComposingElement(){
       // console.log( 'getAddComposingElement' );
    }

    updateTypeOther( otherTypeText ){
        this.otherTypeText = otherTypeText;
    }

    typeChange(){
        if( this.currentEditComposingElement['type'] !== '[other]' && (this.currentEditComposingElement['type'] !== undefined) && (this.currentEditComposingElement['type'].length > 0) ){
            this.typeTrailer = this.currentEditComposingElement['type'];
        }
    }

    molecularFormulaTypeChange(){
        if( this.currentEditComposingElement['molecularFormulaType'] !== '[other]' ){
            this.molecularFormulaTypeTrailer = this.currentEditComposingElement['molecularFormulaType'];
        }
    }

    functionTypeChange(){
        if( this.selectedInherentFunctionType !== '[other]' ){
            this.selectedInherentFunctionTypeTrailer = this.selectedInherentFunctionType;
        }
    }

    addOtherTypeAdd(){
        this.setupData['composingElementTypes'].push( this.otherTypeText );
        this.currentEditComposingElement['type'] = this.otherTypeText;
        this.otherTypeText = '';
    }

    onCancelAdd(){
        this.otherTypeText = '';
        this.currentEditComposingElement['type'] = this.typeTrailer;
        this.nanomaterialService.setNewComposingElementHide();
        this.nanomaterialService.setEditComposingElementHide();


    }

    amountUnitChange(){
        if( this.currentEditComposingElement['valueUnit'] !== '[other]' ){
            this.amountUnitTrailer = this.currentEditComposingElement['valueUnit'];
        }
    }

    otherAmountUnitAdd(){
        this.setupData['composingElementUnits'].push( this.localFormOtherAmountUnitText );
        this.currentEditComposingElement['valueUnit'] = this.localFormOtherAmountUnitText;
        this.localFormOtherAmountUnitText = '';
    }

    otherMolecularAdd(){
        this.setupData['molecularFormulaType'].push( this.otherMolecularFormulaType );
        this.currentEditComposingElement['molecularFormulaType'] = this.otherMolecularFormulaType;
        this.otherMolecularFormulaType = '';
    }

    addNewFunctionType(){
        this.setupData['functionTypes'].push( this.otherFunctionTypeText );
        this.selectedInherentFunctionType = this.otherFunctionTypeText;
        this.otherFunctionTypeText = '';
    }

    cancelNewFunctionType(){
        this.selectedInherentFunctionType = this.selectedInherentFunctionTypeTrailer;
        this.otherFunctionTypeText = '';
    }

    onNewComposingElementSave(){
        this.nanomaterialService.setComposingElementsArray( this.composingElementsArray );
        this.nanomaterialService.setNewComposingElementHide();
        this.nanomaterialService.setEditComposingElementHide();

        // Not new Nanomaterial
        if( this.editData !== undefined ){
            this.apiService.doPost( Consts.SAVE_COMPOSING_ELEMENT, this.editData ).subscribe(
                data => {
                    // console.log( 'Return data from SAVE_COMPOSING_ELEMENT: ', data );
                },
                ( err ) => {
                    console.log( 'ERROR SAVE_COMPOSING_ELEMENT: ', err );
                } );
        }else{
            // New Nanomaterial
            let newComposingElement = {};
            newComposingElement.setValue( 'type', this.currentEditComposingElement['type'] );
            newComposingElement.setValue( 'name', this.currentEditComposingElement['name'] );
            newComposingElement.setValue( 'pubChemDataSourceName', this.currentEditComposingElement['pubChemSourceName'] );
            newComposingElement.setValue( 'pubChemId', this.currentEditComposingElement['pubChemId'] );
            newComposingElement.setValue( 'value', this.currentEditComposingElement['value'] );
            newComposingElement.setValue( 'valueUnit', this.currentEditComposingElement['valueUnit'] );
            newComposingElement.setValue( 'description', this.currentEditComposingElement['description'] );
            let nanomatralObject = {};
            nanomatralObject.setValue( 'type', this.newNanomaterialType );
            nanomatralObject.setValue( 'sampleId', this.sampleId );
            nanomatralObject.setValue( 'description', this.description );
            nanomatralObject.setValue( 'simpleCompBean', newComposingElement );
            this.apiService.doPost( Consts.SAVE_COMPOSING_ELEMENT, nanomatralObject ).subscribe(
                data => {
                    console.log( 'from SAVE_COMPOSING_ELEMENT: ', data['composingElements'] );
                },
                ( err ) => {
                    console.log( 'ERROR SAVE_COMPOSING_ELEMENT: ', err );
                } );
        }
    }

    onNewComposingElementCancel(){
        if( this.editMode && (this.tempHold !== undefined) ){
            this.composingElementsArray[this.currentEditIndex] = { ...this.tempHold };
        }
        this.currentEditComposingElement = {
            type: '',
            name: '',
            pubChemSourceName: '',
            pubChemId: '',
            value: '',
            valueUnit: '',
            molecularFormulaType: '',
            molecularFormula: '',
            description: ''
        }
        this.nanomaterialService.setNewComposingElementHide();
        this.nanomaterialService.setEditComposingElementHide( this.currentEditIndex );
    }

    ngOnDestroy(): void{
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

}
