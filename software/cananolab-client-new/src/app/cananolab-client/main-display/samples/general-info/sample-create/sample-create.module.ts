import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SampleCreateComponent } from './sample-create.component';
import { SampleCreateRoutingModule } from './sample-create-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
    declarations: [SampleCreateComponent],
    imports: [
      CommonModule,
      SampleCreateRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class SampleCreateModule { }

