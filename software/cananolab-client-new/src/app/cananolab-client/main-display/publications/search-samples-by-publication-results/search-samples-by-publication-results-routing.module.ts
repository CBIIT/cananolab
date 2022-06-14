import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchSamplesByPublicationResultsComponent } from './search-samples-by-publication-results.component';

const routes: Routes = [{ path: '', component: SearchSamplesByPublicationResultsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SearchSamplesByPublicationResultsRoutingModule { }
