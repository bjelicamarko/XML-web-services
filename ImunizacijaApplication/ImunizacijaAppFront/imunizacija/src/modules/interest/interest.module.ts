import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SharedModule } from '../shared/shared.module';

import { InterestPageComponent } from './pages/interest-page/interest-page.component';
import { InterestRoutes } from './interest.routes';
import { TestPageComponent } from './pages/test-page/test-page.component';

import { RichTextEditorModule } from '@syncfusion/ej2-angular-richtexteditor';
import { RichTextEditorAllModule } from '@syncfusion/ej2-angular-richtexteditor';

@NgModule({
  declarations: [
    InterestPageComponent,
    TestPageComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(InterestRoutes),
    FormsModule,
    ReactiveFormsModule,
    RichTextEditorModule,
    RichTextEditorAllModule,
  ]
})
export class InterestModule { }
