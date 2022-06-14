import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyFavoritesComponent } from './my-favorites.component';
import { MyFavoritesRoutingModule } from './my-favorites-routing.module';
import { SharedModule } from '../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [MyFavoritesComponent],
    imports: [
      CommonModule,
      MyFavoritesRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class MyFavoritesModule { }
