import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../shared/shared.module';

import { InterestPageComponent } from './pages/interest-page/interest-page.component';
import { InterestRoutes } from './interest.routes';


@NgModule({
  declarations: [
    InterestPageComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(InterestRoutes)
  ]
})
export class InterestModule { }
