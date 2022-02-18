import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SharedModule } from '../shared/shared.module';

import { VaccinePageComponent } from './pages/vaccine-page/vaccine-page.component';
import { VaccineRoutes } from './vaccine.routes';



@NgModule({
  declarations: [
    VaccinePageComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(VaccineRoutes),
    FormsModule,
    ReactiveFormsModule
  ]
})
export class VaccineModule { }
