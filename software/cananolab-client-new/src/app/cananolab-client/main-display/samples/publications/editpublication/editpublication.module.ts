import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditpublicationComponent } from './editpublication.component';
import { EditpublicationRoutingModule } from './editpublication-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OtherDropdownModule } from 'src/app/cananolab-client/common/components/other-dropdown/other-dropdown.module';
@NgModule({
    declarations: [EditpublicationComponent],
    imports: [
      CommonModule,
      EditpublicationRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule,
      OtherDropdownModule
    ]
  })
  export class EditpublicationModule { }
