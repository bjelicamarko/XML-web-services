import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MetaSearchPageComponent } from './pages/meta-search-page/meta-search-page.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { MetaSearchRoutes } from './meta-search.routes';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    MetaSearchPageComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(MetaSearchRoutes),
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class MetaSearchModule { }
