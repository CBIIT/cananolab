import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserResultsComponent } from './user-results.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/cananolab-client/common/modules/set-object-value/shared.module';


@NgModule({
  declarations: [UserResultsComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ],
  exports: [UserResultsComponent]
})
export class UserResultsModule { }
