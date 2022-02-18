import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CitizenDocViewComponent } from './pages/citizen-doc-view/citizen-doc-view.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { UserDocsRoutes } from './user-docs.routes';



@NgModule({
  declarations: [
    CitizenDocViewComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(UserDocsRoutes)
  ]
})
export class UserDocsModule { }
