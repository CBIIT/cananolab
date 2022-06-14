
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchSamplesByPublicationComponent } from './search-samples-by-publication.component';
import { SearchSamplesByPublicationRoutingModule } from './search-samples-by-publication-routing.module';
import { SharedModule } from '../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [SearchSamplesByPublicationComponent],
    imports: [
      CommonModule,
      SearchSamplesByPublicationRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class SearchSamplesByPublicationModule { }
