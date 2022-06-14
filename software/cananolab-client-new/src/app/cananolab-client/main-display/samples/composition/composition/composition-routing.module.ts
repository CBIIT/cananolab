import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CompositionComponent } from './composition.component';
const routes: Routes = [{ path: '', component: CompositionComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompositionRoutingModule { }

