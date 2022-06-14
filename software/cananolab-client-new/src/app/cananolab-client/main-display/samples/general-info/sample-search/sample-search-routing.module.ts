import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SampleSearchComponent } from './sample-search.component';

const routes: Routes = [{ path: '', component: SampleSearchComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SampleSearchRoutingModule { }
