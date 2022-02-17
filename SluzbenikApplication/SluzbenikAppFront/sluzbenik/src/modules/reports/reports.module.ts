import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportsPageComponent } from './pages/reports-page/reports-page.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { ReportsRoutes } from './reports.routes';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MaterialExampleModule } from 'src/material.module';

@NgModule({
  declarations: [
    ReportsPageComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(ReportsRoutes),
    MaterialExampleModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class ReportsModule { }
