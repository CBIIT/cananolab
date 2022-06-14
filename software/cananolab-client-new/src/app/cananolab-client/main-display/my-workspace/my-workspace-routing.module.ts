import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MyWorkspaceComponent } from './my-workspace.component';
const routes: Routes = [{ path: '', component: MyWorkspaceComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyWorkspaceRoutingModule { }

