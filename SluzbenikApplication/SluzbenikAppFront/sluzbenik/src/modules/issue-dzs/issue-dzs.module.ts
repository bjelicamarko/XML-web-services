import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersViewComponent } from './pages/users-view/users-view.component';
import { SharedModule } from '../shared/shared.module';
import { MaterialExampleModule } from 'src/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IssueDZSRoutes } from './issue-dzs.routes';
import { RouterModule } from '@angular/router';
import { CitizenDocViewComponent } from './pages/citizen-doc-view/citizen-doc-view.component';
import { ReasonToDeclineComponent } from './components/reason-to-decline/reason-to-decline.component';



@NgModule({
  declarations: [
    UsersViewComponent,
    CitizenDocViewComponent,
    ReasonToDeclineComponent
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
