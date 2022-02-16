import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SharedModule } from '../shared/shared.module';

import { InterestPageComponent } from './pages/interest-page/interest-page.component';
import { InterestRoutes } from './interest.routes';

@NgModule({
  declarations: [
    InterestPageComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(InterestRoutes),
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class InterestModule { }
