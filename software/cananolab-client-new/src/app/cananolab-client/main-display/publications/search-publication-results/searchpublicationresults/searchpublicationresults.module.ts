import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchpublicationresultsComponent } from './searchpublicationresults.component';
import { SearchpublicationresultsRoutingModule } from './searchpublicationresults-routing.module';
import { SharedModule } from 'src/app/cananolab-client/common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [SearchpublicationresultsComponent],
    imports: [
      CommonModule,
      SearchpublicationresultsRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class SearchpublicationresultsModule { }
