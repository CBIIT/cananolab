import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SampleCreateComponent } from './sample-create.component';
const routes: Routes = [{ path: '', component: SampleCreateComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SampleCreateRoutingModule { }
