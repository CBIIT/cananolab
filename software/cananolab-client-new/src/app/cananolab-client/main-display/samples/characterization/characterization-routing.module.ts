import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CharacterizationComponent } from './characterization.component';
const routes: Routes = [{ path: '', component: CharacterizationComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CharacterizationRoutingModule { }
