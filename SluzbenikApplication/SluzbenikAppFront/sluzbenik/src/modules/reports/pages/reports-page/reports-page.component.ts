import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import * as moment from 'moment';
import { DocumentProviderService } from 'src/modules/shared/services/document-provider.service';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UtilService } from 'src/modules/shared/services/util.service';
import { ReportsService } from '../../services/reports.service';

@Component({
  selector: 'app-reports-page',
  templateUrl: './reports-page.component.html',
  styleUrls: ['./reports-page.component.scss']
})
export class ReportsPageComponent {

  public range = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });
  
  idDocument: string = '';

  constructor(private reportsService: ReportsService, private snackBarService: SnackBarService,
    private utilService: UtilService, private documentProviderService: DocumentProviderService) { }

  public changeDate(): void {  
    console.log(this.range);
    if (this.range.value.start && this.range.value.end) {
      let dateFrom : string = this.checkDate(this.range.value.start);
      let dateTo : string = this.checkDate(this.range.value.end);

      this.reportsService.getReports(dateFrom, dateTo)
        .subscribe((response) => {
          this.snackBarService.openSnackBar(response.body as string);
          this.idDocument = response.body as string;
        })
    }
  
  }
 

  checkDate(date: string) : string {
    var dateString = moment(date).format('YYYY-MM-DD');
    return dateString.toString();
  }

  getPdf() {
    this.documentProviderService.getDocumentPDF('izvestaji', this.idDocument).subscribe((response) => {
      if(response.body)
        this.utilService.downloadPDFDocument(response.body, 'izvestaji');
    }, 
    (error) => {
      this.snackBarService.openSnackBarFast("Doslo je do greške prilikom preuzimanja/prikazivanja dokumenta.");
    });
  }

  getHtml() {
    this.documentProviderService.getDocumentHTML('izvestaji', this.idDocument).subscribe((response) => {
      if(response.body)
        this.utilService.openHtmlDocumentInNewTab(response.body);
    }, 
    (error) => {
      this.snackBarService.openSnackBarFast("Doslo je do greške prilikom prikazivanja dokumenta.");
    });
  }
}
