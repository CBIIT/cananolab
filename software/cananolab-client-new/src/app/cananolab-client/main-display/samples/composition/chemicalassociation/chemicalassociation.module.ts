import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChemicalassociationComponent } from './chemicalassociation.component';
import { ChemicalassociationRoutingModule } from './chemicalassociation-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OtherDropdownModule } from 'src/app/cananolab-client/common/components/other-dropdown/other-dropdown.module';
import { FileModule } from 'src/app/cananolab-client/common/components/file/file.module';
@NgModule({
    declarations: [ChemicalassociationComponent],
    imports: [
      CommonModule,
      ChemicalassociationRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule,
      OtherDropdownModule,
      FileModule
    ]
  })
export class ChemicalassociationModule { }

