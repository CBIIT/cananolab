import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SampleViewComponent } from './sample-view.component';
import { SampleViewRoutingModule } from './sample-view-routing.module';
import { SharedModule } from '../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [SampleViewComponent],
    imports: [
      CommonModule,
      SampleViewRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
export class SampleViewModule { }

