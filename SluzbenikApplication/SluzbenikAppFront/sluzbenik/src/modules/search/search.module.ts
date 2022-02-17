import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchPageComponent } from './pages/search-page/search-page.component';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SearchRoutes } from './search.routes';



@NgModule({
  declarations: [
    SearchPageComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(SearchRoutes),
    FormsModule,
    ReactiveFormsModule
  ]
})
export class SearchModule { }
