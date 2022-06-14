import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SampleEditComponent } from './sample-edit.component';
import { SampleEditRoutingModule } from './sample-edit-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
    declarations: [SampleEditComponent],
    imports: [
      CommonModule,
      SampleEditRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class SampleEditModule { }

