import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CharacterizationComponent } from './characterization.component';
import { CharacterizationRoutingModule } from './characterization-routing.module';
import { SharedModule } from 'src/app/cananolab-client/common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [CharacterizationComponent],
    imports: [
      CommonModule,
      CharacterizationRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
export class CharacterizationModule { }

