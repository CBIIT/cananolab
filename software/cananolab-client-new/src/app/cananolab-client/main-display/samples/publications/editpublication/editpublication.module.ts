import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditpublicationComponent } from './editpublication.component';
import { EditpublicationRoutingModule } from './editpublication-routing.module';
import { SharedModule } from 'src/app/cananolab-client/common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [EditpublicationComponent],
    imports: [
      CommonModule,
      EditpublicationRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class EditpublicationModule { }
