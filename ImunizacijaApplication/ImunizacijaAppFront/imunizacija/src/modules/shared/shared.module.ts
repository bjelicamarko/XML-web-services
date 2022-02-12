import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DateFormatPipe } from './pipes/date-format.pipe';
import { ConformationDialogComponent } from './components/conformation-dialog/conformation-dialog.component';
import { ScriptService } from './services/script.service';


@NgModule({
  declarations: [
    DateFormatPipe,
    ConformationDialogComponent
  ],
  imports: [
    CommonModule
  ],
  providers: [
    ScriptService
  ]
})
export class SharedModule { }
