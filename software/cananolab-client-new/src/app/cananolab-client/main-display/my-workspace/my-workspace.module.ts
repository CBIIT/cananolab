import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyWorkspaceComponent } from './my-workspace.component';
import { MyWorkspaceRoutingModule } from './my-workspace-routing.module';
import { SharedModule } from '../../common/modules/set-object-value/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    declarations: [MyWorkspaceComponent],
    imports: [
      CommonModule,
      MyWorkspaceRoutingModule,
      SharedModule,
      FormsModule,
      ReactiveFormsModule
    ]
  })
  export class MyWorkspaceModule { }
