import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RightSideBarComponent } from './right-side-bar.component';
import { HomeFeaturesModule } from './home-features/home-features.module';
import { HomeHowToModule } from './home-how-to/home-how-to.module';
import { HomeKeepingUpWithCananolabModule } from './home-keeping-up-with-cananolab/home-keeping-up-with-cananolab.module';
import { HomeUserActionsModule } from './home-user-actions/home-user-actions.module';
import { HomeWhatsNewModule } from './home-whats-new/home-whats-new.module';
@NgModule({
  declarations: [RightSideBarComponent],
  imports: [
    CommonModule,
    HomeFeaturesModule,
    HomeHowToModule,
    HomeKeepingUpWithCananolabModule,
    HomeUserActionsModule,
    HomeWhatsNewModule
  ],
  exports: [RightSideBarComponent]
})
export class RightSideBarModule { }
