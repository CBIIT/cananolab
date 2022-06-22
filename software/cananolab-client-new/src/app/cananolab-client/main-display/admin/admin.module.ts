import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminComponent } from './admin.component';
import { AdminRoutingModule } from './admin-routing.module';
import { SharedModule } from '../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserResultsModule } from './user-results/user-results.module';
@NgModule({
    declarations: [AdminComponent],
    imports: [
      CommonModule,
      AdminRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule,
      UserResultsModule
    ]
  })
  export class AdminModule { }
