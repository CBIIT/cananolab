import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { HomeRoutingModule } from './home-routing.module';
import { SharedModule } from 'src/app/cananolab-client/common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [HomeComponent],
    imports: [
      CommonModule,
      HomeRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
export class HomeModule { }

