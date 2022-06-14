import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProtocolSearchComponent } from './protocol-search.component';
import { ProtocolSearchRoutingModule } from './protocol-search-routing.module';
import { SharedModule } from 'src/app/cananolab-client/common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [ProtocolSearchComponent],
    imports: [
      CommonModule,
      ProtocolSearchRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class ProtocolSearchModule { }
