import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompositionComponent } from './composition.component';
import { CompositionRoutingModule } from './composition-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [CompositionComponent],
    imports: [
      CommonModule,
      CompositionRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class CompositionModule { }
