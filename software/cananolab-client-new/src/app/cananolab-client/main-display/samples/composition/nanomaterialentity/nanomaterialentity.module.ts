import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NanomaterialentityComponent } from './nanomaterialentity.component';
import { NanomaterialentityRoutingModule } from './nanomaterialentity-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [NanomaterialentityComponent],
    imports: [
      CommonModule,
      NanomaterialentityRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
export class NanomaterialentityModule { }

