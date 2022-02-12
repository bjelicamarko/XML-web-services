import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DateFormatPipe } from './pipes/date-format.pipe';
import { ConformationDialogComponent } from './components/conformation-dialog/conformation-dialog.component';


@NgModule({
  declarations: [
    DateFormatPipe,
    ConformationDialogComponent
  ],
  imports: [
    CommonModule
  ]
})
export class SharedModule { }
