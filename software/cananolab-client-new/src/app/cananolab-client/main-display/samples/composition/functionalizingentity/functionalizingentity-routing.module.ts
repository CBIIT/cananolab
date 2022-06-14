import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FunctionalizingentityComponent } from './functionalizingentity.component';
const routes: Routes = [{ path: '', component: FunctionalizingentityComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FunctionalizingentityRoutingModule { }

