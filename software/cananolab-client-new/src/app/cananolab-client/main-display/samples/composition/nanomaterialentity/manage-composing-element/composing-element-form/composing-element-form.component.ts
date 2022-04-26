import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../../../../common/services/api.service';
import { Consts } from '../../../../../../../constants';
import { NanomaterialService } from '../../nanomaterial.service';
import { UtilService } from '../../../../../../common/services/util.service';

@Component( {
    selector: 'canano-composing-element-form',
    templateUrl: './composing-element-form.component.html',
    styleUrls: ['../../../../../../../btn-bravo-canano.scss', './composing-element-form.component.scss'] // @TODO change to import
    // styleUrls: ['./composing-element-form.component.scss']
} )
export class ComposingElementFormComponent implements OnInit{

    // This will (later) be wired to a button in manage-composing-element
    showAddComposingElement = true;
    type;
    typeTrailer = '';
    otherTypeText;
    localFormOtherAmountUnitText;
    amountUnitTrailer = '';
    otherMolecularFormulaType;
    molecularFormulaTypeTrailer;
    selectedInherentFunctionType;
    showAddInherentFunction;

    editData;
    setupData;
    elementName = '';
    composingElementFormType;
    composingElementFormName;
    pubChemDataSourceName;
    pubChemId;
    composingElementFormValue;
    composingElementFormValueUnit;
    composingElementFormMolecularFormulaType;
    composingElementForDescription;

    constructor( private apiService: ApiService, private nanomaterialService: NanomaterialService,
                 private utilService: UtilService){
    }

    ngOnInit(): void{
        this.init();
    }

    async init(){
        this.editData = undefined;
        this.setupData = undefined;
        while( this.editData === undefined || this.setupData === undefined  ){
            this.editData = this.nanomaterialService.getNanomaterialEditData();
            this.setupData = this.nanomaterialService.getNanomaterialSetupData();
            await this.utilService.sleep( 250 );
        }

    }

    getAddComposingElement(){
        console.log( 'MHL getAddComposingElement' );
    }

    updateTypeOther(otherTypeText){
        this.otherTypeText = otherTypeText;
    }

    typeChange(){
        if( this.composingElementFormType !== '[other]' ){
            this.typeTrailer = this.composingElementFormType;
        }
    }

    molecularFormulaTypeChange(){
        if( this.otherMolecularFormulaType !== '[other]' ){
            this.molecularFormulaTypeTrailer = this.otherMolecularFormulaType;
        }
    }

    addOtherTypeAdd(){
        this.setupData['composingElementTypes'].push(this.otherTypeText);
        this.composingElementFormType = this.otherTypeText;
        this.otherTypeText = '';
    }

    amountUnitChange(){
        if( this.composingElementFormValueUnit !== '[other]' ){
            this.amountUnitTrailer = this.composingElementFormValueUnit;
        }
    }

    otherAmountUnitAdd(){
        this.setupData['composingElementUnits'].push(this.localFormOtherAmountUnitText);
        this.composingElementFormValueUnit = this.localFormOtherAmountUnitText;
        this.localFormOtherAmountUnitText = '';
    }

    otherMolecularAdd(){
        this.setupData['molecularFormulaTypes'].push(this.otherMolecularFormulaType);
        this.composingElementFormMolecularFormulaType = this.otherMolecularFormulaType;
        this.otherMolecularFormulaType = '';
    }
}
