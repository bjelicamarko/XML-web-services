import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DateFormatPipe } from './pipes/date-format.pipe';
import { ConformationDialogComponent } from './components/conformation-dialog/conformation-dialog.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Interceptor } from './interceptors/interceptor.interceptor';


@NgModule({
  declarations: [
    DateFormatPipe,
    ConformationDialogComponent
  ],
  imports: [
    CommonModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true }
  ]
})
export class SharedModule { }
