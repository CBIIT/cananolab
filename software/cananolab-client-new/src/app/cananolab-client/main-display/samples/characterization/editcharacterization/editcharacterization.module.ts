import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditcharacterizationRoutingModule } from './editcharacterization-routing.module';
import { EditcharacterizationComponent } from './editcharacterization.component';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OtherDropdownModule } from 'src/app/cananolab-client/common/components/other-dropdown/other-dropdown.module';
import { DisclaimerModule } from 'src/app/cananolab-client/common/components/disclaimer/disclaimer.module';
@NgModule({
    declarations: [EditcharacterizationComponent],
    imports: [
      CommonModule,
      EditcharacterizationRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule,
      OtherDropdownModule,
      DisclaimerModule
    ]
  })
export class EditcharacterizationModule { }

