import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProtocolSearchResultsComponent } from './protocol-search-results.component';
const routes: Routes = [{ path: '', component: ProtocolSearchResultsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProtocolSearchResultsRoutingModule { }

