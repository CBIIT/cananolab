import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SampleEditComponent } from './sample-edit.component';
import { SampleEditRoutingModule } from './sample-edit-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OtherDropdownModule } from 'src/app/cananolab-client/common/components/other-dropdown/other-dropdown.module';


@NgModule({
    declarations: [SampleEditComponent],
    imports: [
      CommonModule,
      SampleEditRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule,
      OtherDropdownModule
    ]
  })
  export class SampleEditModule { }

