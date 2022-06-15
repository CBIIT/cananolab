import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeFeaturesComponent } from './home-features.component';


@NgModule({
  declarations: [HomeFeaturesComponent],
  imports: [
    CommonModule,
  ],
  exports: [HomeFeaturesComponent]
})
export class HomeFeaturesModule { }
