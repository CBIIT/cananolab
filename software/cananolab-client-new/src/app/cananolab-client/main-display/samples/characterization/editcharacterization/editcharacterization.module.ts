import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditcharacterizationRoutingModule } from './editcharacterization-routing.module';
import { EditcharacterizationComponent } from './editcharacterization.component';
import { SharedModule } from 'src/app/cananolab-client/common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [EditcharacterizationComponent],
    imports: [
      CommonModule,
      EditcharacterizationRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
export class EditcharacterizationModule { }

