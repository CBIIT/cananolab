import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProtocolSearchComponent } from './protocol-search.component';
const routes: Routes = [{ path: '', component: ProtocolSearchComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProtocolSearchRoutingModule { }
