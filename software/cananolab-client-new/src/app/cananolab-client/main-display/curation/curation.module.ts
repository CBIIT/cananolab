import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CurationComponent } from './curation.component';
import { CurationRoutingModule } from './curation-routing.module';
import { SharedModule } from '../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [CurationComponent],
    imports: [
      CommonModule,
      CurationRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class CurationModule { }
