import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchSamplesByPublicationComponent } from './search-samples-by-publication.component';

const routes: Routes = [{ path: '', component: SearchSamplesByPublicationComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SearchSamplesByPublicationRoutingModule { }
