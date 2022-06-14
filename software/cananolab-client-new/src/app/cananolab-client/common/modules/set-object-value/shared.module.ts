import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainDisplayHeadingComponent } from 'src/app/cananolab-client/main-display/main-display-heading/main-display-heading.component';
import { LoaderComponent } from '../../components/loader/loader.component';
import { SampleAvailabilityDisplayComponent } from 'src/app/cananolab-client/main-display/samples/general-info/sample-search/sample-search-results/sample-availability-display/sample-availability-display.component';
import { SearchResultsPagerComponent } from '../../components/search-results-pager/search-results-pager.component';
const COMPONENT=[
    MainDisplayHeadingComponent,
    LoaderComponent,
    SampleAvailabilityDisplayComponent,
    SearchResultsPagerComponent,
]

@NgModule({
  declarations: [
      COMPONENT
  ],
  imports: [
    CommonModule
  ],
  exports: [
      COMPONENT
  ]
})
export class SharedModule { }
