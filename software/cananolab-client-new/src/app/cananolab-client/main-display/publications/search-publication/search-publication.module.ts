import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchPublicationComponent } from './search-publication.component';
import { SearchPublicationRoutingModule } from './search-publication-routing.module';
import { SharedModule } from '../../../common/modules/set-object-value/shared.module';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';

@NgModule({
    declarations: [SearchPublicationComponent],
    imports: [
      CommonModule,
      SearchPublicationRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class SearchPublicationModule { }

