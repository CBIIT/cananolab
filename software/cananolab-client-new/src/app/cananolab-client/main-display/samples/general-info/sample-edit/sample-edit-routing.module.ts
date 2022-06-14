import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SampleEditComponent } from './sample-edit.component';
const routes: Routes = [{ path: '', component: SampleEditComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SampleEditRoutingModule { }
