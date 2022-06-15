import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeWhatsNewComponent } from './home-whats-new.component';


@NgModule({
  declarations: [HomeWhatsNewComponent],
  imports: [
    CommonModule
  ],
  exports: [HomeWhatsNewComponent]
})
export class HomeWhatsNewModule { }
