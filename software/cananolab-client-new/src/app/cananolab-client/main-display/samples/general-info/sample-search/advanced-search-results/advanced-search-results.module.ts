import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdvancedSearchResultsComponent } from './advanced-search-results.component';
import { AdvancedSearchResultsRoutingModule } from './advanced-search-results-routing.module';
import { SharedModule } from '../../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
    declarations: [AdvancedSearchResultsComponent],
    imports: [
      CommonModule,
      AdvancedSearchResultsRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class AdvnacedSearchResultsModule { }
