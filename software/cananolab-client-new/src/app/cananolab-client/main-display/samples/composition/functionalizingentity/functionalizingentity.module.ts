import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FunctionalizingentityComponent } from './functionalizingentity.component';
import { FunctionalizingentityRoutingModule } from './functionalizingentity-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [FunctionalizingentityComponent],
    imports: [
      CommonModule,
      FunctionalizingentityRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class FunctionalizingentityModule { }
