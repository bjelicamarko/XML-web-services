import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DateFormatPipe } from './pipes/date-format.pipe';
import { ConformationDialogComponent } from './components/conformation-dialog/conformation-dialog.component';
import { ScriptService } from './services/script.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Interceptor } from './interceptors/interceptor.interceptor';
import { PaginationComponent } from './components/pagination/pagination.component';


@NgModule({
  declarations: [
    DateFormatPipe,
    ConformationDialogComponent,
    PaginationComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    PaginationComponent
  ],
  providers: [
    ScriptService,
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true }
  ]
})
export class SharedModule { }
