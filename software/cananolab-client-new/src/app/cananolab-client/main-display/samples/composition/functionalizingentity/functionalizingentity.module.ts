import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FunctionalizingentityComponent } from './functionalizingentity.component';
import { FunctionalizingentityRoutingModule } from './functionalizingentity-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OtherDropdownModule } from 'src/app/cananolab-client/common/components/other-dropdown/other-dropdown.module';
import { FileModule } from 'src/app/cananolab-client/common/components/file/file.module';
@NgModule({
    declarations: [FunctionalizingentityComponent],
    imports: [
      CommonModule,
      FunctionalizingentityRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule,
      OtherDropdownModule,
      FileModule
    ]
  })
  export class FunctionalizingentityModule { }
