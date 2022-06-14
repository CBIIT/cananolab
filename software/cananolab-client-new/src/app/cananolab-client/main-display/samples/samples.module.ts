import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SamplesComponent } from './samples.component';
import { SamplesRoutingModule } from './samples-routing.module';
import { SharedModule } from '../../common/modules/set-object-value/shared.module';
@NgModule({
    declarations: [SamplesComponent],
    imports: [
      CommonModule,
      SamplesRoutingModule,
      SharedModule
    ]
  })
  export class SamplesModule { }
