import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchpublicationresultsComponent } from './searchpublicationresults.component';
import { SearchpublicationresultsRoutingModule } from './searchpublicationresults-routing.module';
import { SharedModule } from '../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SearchpublicationresultsPipe } from './searchpublicationresults.pipe';
import { DisclaimerModule } from 'src/app/cananolab-client/common/components/disclaimer/disclaimer.module';
@NgModule({
    declarations: [SearchpublicationresultsComponent, SearchpublicationresultsPipe],
    imports: [
      CommonModule,
      SearchpublicationresultsRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule,
      DisclaimerModule
    ]
  })
  export class SearchpublicationresultsModule { }
