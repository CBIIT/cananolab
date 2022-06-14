import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProtocolSearchResultsComponent } from './protocol-search-results.component';
import { ProtocolSearchResultsRoutingModule } from './protocol-search-results-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [ProtocolSearchResultsComponent],
    imports: [
      CommonModule,
      ProtocolSearchResultsRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class ProtocolSearchResultsModule { }
