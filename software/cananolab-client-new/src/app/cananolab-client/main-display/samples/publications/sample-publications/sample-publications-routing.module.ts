import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SamplePublicationsComponent } from './sample-publications.component';
const routes: Routes = [{ path: '', component: SamplePublicationsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SamplePublicationsRoutingModule { }
