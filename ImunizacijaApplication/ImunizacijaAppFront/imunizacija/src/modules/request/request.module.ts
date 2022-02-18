import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RequestPageComponent } from './pages/request-page/request-page.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { RequestRoutes } from './request.routes';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { RichTextEditorModule } from '@syncfusion/ej2-angular-richtexteditor';
import { RichTextEditorAllModule } from '@syncfusion/ej2-angular-richtexteditor';

@NgModule({
  declarations: [
    RequestPageComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(RequestRoutes),
    FormsModule,
    ReactiveFormsModule,
    RichTextEditorModule,
    RichTextEditorAllModule
  ]
})
export class RequestModule { }
