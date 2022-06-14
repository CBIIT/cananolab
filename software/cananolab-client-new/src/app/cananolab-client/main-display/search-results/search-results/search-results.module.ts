import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchResultsComponent } from './search-results.component';
import { SearchResultsRoutingModule } from './search-results-routing/search-results-routing.module';
import { SharedModule } from '../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [SearchResultsComponent],
    imports: [
      CommonModule,
      SearchResultsRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class SearchResultsModule { }
