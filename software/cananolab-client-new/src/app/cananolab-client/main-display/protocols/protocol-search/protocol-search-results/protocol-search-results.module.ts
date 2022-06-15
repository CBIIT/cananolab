import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProtocolSearchResultsComponent } from './protocol-search-results.component';
import { ProtocolSearchResultsRoutingModule } from './protocol-search-results-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProtocolSearchResultsDisplayTitlePipe } from './protocol-search-results-display-title.pipe';
import { ProtocolSearchResultsDisplayDescriptionPipe } from './protocol-search-results-display-description.pipe';
import { ProtocolSearchResultsDisplayHrefPipe } from './protocol-search-results-display-href.pipe';
@NgModule({
    declarations: [
        ProtocolSearchResultsComponent,
        ProtocolSearchResultsDisplayTitlePipe,
        ProtocolSearchResultsDisplayDescriptionPipe,
        ProtocolSearchResultsDisplayHrefPipe
        ],
    imports: [
      CommonModule,
      ProtocolSearchResultsRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class ProtocolSearchResultsModule { }
