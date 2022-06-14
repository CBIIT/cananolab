import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdvancedSearchComponent } from './advanced-search.component';
import { AdvancedSearchRoutingModule } from './advanced-search-routing.module';
import { SharedModule } from '../../../../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
    declarations: [AdvancedSearchComponent],
    imports: [
      CommonModule,
      AdvancedSearchRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class AdvancedSearchModule { }

