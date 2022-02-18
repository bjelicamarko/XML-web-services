import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml" });

  constructor(private http: HttpClient) { }

  getUser(userId: string): Observable<HttpResponse<string>> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.get<HttpResponse<string>>("indirekcija/api/users/" + userId, queryParams);
  }

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

  parseXml(xmlStr: string): any {
    var result;
    var parser = require('xml2js');
    parser.Parser().parseString(xmlStr, (e: any, r: any) => { result = r });
    return result;
  }
}
