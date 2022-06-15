import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HomeUserActionsComponent} from './home-user-actions.component'
import { LoginModule } from './login/login.module';
@NgModule({
  declarations: [HomeUserActionsComponent],
  imports: [
    CommonModule,
    LoginModule
  ],
  exports:[HomeUserActionsComponent]
})
export class HomeUserActionsModule { }
