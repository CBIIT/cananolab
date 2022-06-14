import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProtocolsComponent } from './protocols.component';
import { ProtocolsRoutingModule } from './protocols-routing.module';
import { SharedModule } from '../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
    declarations: [ProtocolsComponent],
    imports: [
      CommonModule,
      ProtocolsRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class ProtocolsModule { }

