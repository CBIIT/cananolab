import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchPublicationComponent } from './search-publication.component';
const routes: Routes = [{ path: '', component: SearchPublicationComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SearchPublicationRoutingModule { }
