import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChemicalassociationComponent } from './chemicalassociation.component';
const routes: Routes = [{ path: '', component: ChemicalassociationComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ChemicalassociationRoutingModule { }
