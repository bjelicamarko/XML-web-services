import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SharedModule } from '../shared/shared.module';

import { ConsentPageComponent } from './pages/consent-page/consent-page.component';
import { ConsentRoutes } from './consent.routes';
import { TestPageComponent } from './pages/test-page/test-page.component';



@NgModule({
  declarations: [
    ConsentPageComponent,
    TestPageComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(ConsentRoutes),
    FormsModule,
    ReactiveFormsModule
  ]
})
export class ConsentModule { }
