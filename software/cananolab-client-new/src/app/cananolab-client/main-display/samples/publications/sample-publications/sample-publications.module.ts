import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SamplePublicationsComponent } from './sample-publications.component';
import { SamplePublicationsRoutingModule } from './sample-publications-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [SamplePublicationsComponent],
    imports: [
      CommonModule,
      SamplePublicationsRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
export class SamplePublicationsModule { }

