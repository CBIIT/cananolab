import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchpublicationresultsComponent } from './searchpublicationresults.component';
const routes: Routes = [{ path: '', component: SearchpublicationresultsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SearchpublicationresultsRoutingModule { }
