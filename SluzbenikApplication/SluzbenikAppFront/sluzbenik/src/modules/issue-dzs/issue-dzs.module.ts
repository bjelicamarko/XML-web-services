import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersViewComponent } from './pages/users-view/users-view.component';
import { SharedModule } from '../shared/shared.module';
import { MaterialExampleModule } from 'src/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IssueDZSRoutes } from './issue-dzs.routes';
import { RouterModule } from '@angular/router';
import { CitizenDocViewComponent } from './pages/citizen-doc-view/citizen-doc-view.component';



@NgModule({
  declarations: [
    UsersViewComponent,
    CitizenDocViewComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(IssueDZSRoutes),
    MaterialExampleModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class IssueDzsModule { }
