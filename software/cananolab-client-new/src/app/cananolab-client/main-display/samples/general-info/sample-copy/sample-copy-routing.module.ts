import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SampleCopyComponent } from './sample-copy.component';
const routes: Routes = [{ path: '', component: SampleCopyComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SampleCopyRoutingModule { }
