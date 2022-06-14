import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CurationComponent } from './curation.component';
const routes: Routes = [{ path: '', component: CurationComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CurationRoutingModule { }

