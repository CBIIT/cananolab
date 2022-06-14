import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CompositionfileComponent } from './compositionfile.component';
const routes: Routes = [{ path: '', component: CompositionfileComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompositionfileRoutingModule { }

