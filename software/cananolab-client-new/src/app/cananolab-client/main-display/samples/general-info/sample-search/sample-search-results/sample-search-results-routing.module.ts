import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SampleSearchResultsComponent } from './sample-search-results.component';
const routes: Routes = [{ path: '', component: SampleSearchResultsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SampleSearchResultsRoutingModule { }
