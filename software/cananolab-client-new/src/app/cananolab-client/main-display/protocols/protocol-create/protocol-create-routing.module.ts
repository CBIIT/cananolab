import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProtocolCreateComponent } from './protocol-create.component';

const routes: Routes = [{ path: '', component: ProtocolCreateComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProtocolCreateRoutingModule { }
