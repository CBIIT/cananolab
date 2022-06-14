import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChemicalassociationComponent } from './chemicalassociation.component';
import { ChemicalassociationRoutingModule } from './chemicalassociation-routing.module';
import { SharedModule } from 'src/app/cananolab-client/common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [ChemicalassociationComponent],
    imports: [
      CommonModule,
      ChemicalassociationRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
export class ChemicalassociationModule { }

