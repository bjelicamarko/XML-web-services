import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor() { }

  public getLoggedUserRole(): string {
    const item = localStorage.getItem("user");

    if (item) {
      const jwt: JwtHelperService = new JwtHelperService();
      return jwt.decodeToken(item).role;
    }
    return "";
  }

  public getLoggedUserID(): string {
    const item = localStorage.getItem("user");

    if (item) {
      const jwt: JwtHelperService = new JwtHelperService();
      return jwt.decodeToken(item).sub;
    }
    return "";
  }

  public getNoPages(totalItems: number, pageSize: number): number {
    return Math.ceil(totalItems / pageSize);
  }

  public openHtmlDocumentInNewTab(html: string): void {
    let wnd = window.open("about:blank", "_blank");
    if (wnd != null)
      wnd.document.write(html);
    wnd?.document.close();
  }

  public downloadPDFDocument(_blob: any, name: string = 'dokument'): void {
    let blob = new Blob([_blob], { type: 'application/pdf' });
    let pdfUrl = window.URL.createObjectURL(blob);

    var PDF_link = document.createElement('a');
    PDF_link.href = pdfUrl;

    //   TO OPEN PDF ON BROWSER IN NEW TAB
    window.open(pdfUrl, '_blank');

    //   TO DOWNLOAD PDF TO YOUR COMPUTER
    PDF_link.download = name + ".pdf";
    PDF_link.click();
  }

  public openPDFDocumentOnly(_blob: any, name: string = 'dokument'): void {
    let blob = new Blob([_blob], { type: 'application/pdf' });
    let pdfUrl = window.URL.createObjectURL(blob);

    var PDF_link = document.createElement('a');
    PDF_link.href = pdfUrl;

    //   TO OPEN PDF ON BROWSER IN NEW TAB
    window.open(pdfUrl, '_blank');
  }

}
