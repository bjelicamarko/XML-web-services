import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RequestPageComponent } from './pages/request-page/request-page.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { RequestRoutes } from './request.routes';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    RequestPageComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(RequestRoutes),
    FormsModule,
    ReactiveFormsModule
  ]
})
export class RequestModule { }
