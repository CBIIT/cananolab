import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FileComponent } from './file.component';
import { SharedModule } from '../../modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
    declarations: [FileComponent],
    imports: [
      CommonModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ],
    exports:[
        FileComponent
    ]
  })
  export class FileModule { }

