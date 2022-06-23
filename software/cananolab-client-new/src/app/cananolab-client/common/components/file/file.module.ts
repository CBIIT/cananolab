import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FileComponent } from './file.component';
import { SharedModule } from '../../modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DisclaimerModule } from '../disclaimer/disclaimer.module';
@NgModule({
    declarations: [FileComponent],
    imports: [
      CommonModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule,
      DisclaimerModule
    ],
    exports:[
        FileComponent,
    ]
  })
  export class FileModule { }

