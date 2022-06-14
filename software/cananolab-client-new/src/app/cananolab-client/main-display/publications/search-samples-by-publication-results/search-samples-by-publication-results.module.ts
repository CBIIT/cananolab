import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchSamplesByPublicationResultsComponent } from './search-samples-by-publication-results.component';
import { SearchSamplesByPublicationResultsRoutingModule } from './search-samples-by-publication-results-routing.module';
import { SharedModule } from '../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [SearchSamplesByPublicationResultsComponent],
    imports: [
      CommonModule,
      SearchSamplesByPublicationResultsRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class SearchSamplesByPublicationResultsModule { }
