import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OtherDropdownComponent } from './other-dropdown.component';
import { SharedModule } from '../../modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
    declarations: [OtherDropdownComponent],
    imports: [
      CommonModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ],
    exports:[
        OtherDropdownComponent
    ]
  })
  export class OtherDropdownModule { }

