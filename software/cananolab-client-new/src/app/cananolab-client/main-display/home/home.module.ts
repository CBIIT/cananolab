import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { HomeRoutingModule } from './home-routing.module';
import { SharedModule } from '../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowseCananolabModule } from './browse-cananolab/browse-cananolab.module';
import { RightSideBarModule } from './right-side-bar/right-side-bar.module';
@NgModule({
    declarations: [HomeComponent],
    imports: [
      CommonModule,
      HomeRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule,
      BrowseCananolabModule,
      RightSideBarModule
    ]
  })
  export class HomeModule { }
