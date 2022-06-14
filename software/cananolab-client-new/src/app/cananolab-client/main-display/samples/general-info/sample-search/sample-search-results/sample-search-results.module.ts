import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SampleSearchResultsComponent } from './sample-search-results.component';
import { SampleSearchResultsRoutingModule } from './sample-search-results-routing.module';
import { SharedModule } from 'src/app/cananolab-client/common/modules/set-object-value/shared.module';
import { FormsModule } from '@angular/forms';


@NgModule({
    declarations: [SampleSearchResultsComponent],
    imports: [
      CommonModule,
      SampleSearchResultsRoutingModule,
      SharedModule,
      FormsModule
    ]
  })
  export class SampleSearchResultsModule { }
