import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DisclaimerComponent } from './disclaimer.component';


@NgModule({
  declarations: [DisclaimerComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports:[DisclaimerComponent]
})
export class DisclaimerModule { }
