import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompositionfileComponent } from './compositionfile.component';
import { CompositionfileRoutingModule } from './compositionfile-routing.module';
import { SharedModule } from 'src/app/cananolab-client/common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [CompositionfileComponent],
    imports: [
      CommonModule,
      CompositionfileRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class CompositionfileModule { }
