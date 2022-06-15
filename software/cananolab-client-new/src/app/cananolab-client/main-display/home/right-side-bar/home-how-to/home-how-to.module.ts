import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeHowToComponent } from './home-how-to.component';


@NgModule({
  declarations: [HomeHowToComponent],
  imports: [
    CommonModule
  ],
  exports:[HomeHowToComponent]
})
export class HomeHowToModule { }
