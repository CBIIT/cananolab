import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdvancedSearchResultsComponent } from './advanced-search-results.component';
const routes: Routes = [{ path: '', component: AdvancedSearchResultsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdvancedSearchResultsRoutingModule { }
