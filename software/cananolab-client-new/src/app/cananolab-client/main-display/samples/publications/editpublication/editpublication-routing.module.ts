import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EditpublicationComponent } from './editpublication.component';

const routes: Routes = [{ path: '', component: EditpublicationComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EditpublicationRoutingModule { }
