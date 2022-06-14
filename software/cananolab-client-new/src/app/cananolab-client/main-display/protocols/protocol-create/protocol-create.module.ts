import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProtocolCreateComponent } from './protocol-create.component';
import { ProtocolCreateRoutingModule } from './protocol-create-routing.module';
import { SharedModule } from '../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [ProtocolCreateComponent],
    imports: [
      CommonModule,
      ProtocolCreateRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class ProtocolCreateModule { }
