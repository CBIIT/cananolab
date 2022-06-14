import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NanomaterialentityComponent } from './nanomaterialentity.component';
const routes: Routes = [{ path: '', component: NanomaterialentityComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NanomaterialentityRoutingModule { }
