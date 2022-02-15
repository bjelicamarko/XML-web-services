import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersViewComponent } from './pages/users-view/users-view.component';
import { SharedModule } from '../shared/shared.module';
import { MaterialExampleModule } from 'src/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    UsersViewComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    //RouterModule.forChild(UsersRoutes),
    MaterialExampleModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class IssueDzsModule { }
