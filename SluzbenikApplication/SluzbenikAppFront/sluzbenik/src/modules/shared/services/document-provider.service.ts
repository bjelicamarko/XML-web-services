import { Injectable,  } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DocumentProviderService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml"});

  constructor(private http: HttpClient) {}

  getDocumentHTML(documentName: string, documentId: string): Observable<HttpResponse<string>> {
    let queryParams = {};
    queryParams = {
      headers: this.headers, 
      observe: "response",
      responseType: "text"
    };
    return this.http.get<HttpResponse<string>>(`indirekcija/api/${documentName}/generateHTML/${documentId}.xml`, queryParams);
  }

  getDocumentPDF(documentName: string, documentId: string): Observable<HttpResponse<string>> {
    let queryParams = {};
    queryParams = {
      headers: this.headers, 
      observe: "response",
      responseType: "blob"
    };
    return this.http.get<HttpResponse<string>>(`indirekcija/api/${documentName}/generatePDF/${documentId}.xml`, queryParams);
  }
}