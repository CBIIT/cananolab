import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EditcharacterizationComponent } from './editcharacterization.component';
const routes: Routes = [{ path: '', component: EditcharacterizationComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EditcharacterizationRoutingModule { }
