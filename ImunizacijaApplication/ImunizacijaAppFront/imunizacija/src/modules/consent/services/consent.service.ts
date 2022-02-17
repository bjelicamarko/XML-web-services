import { Injectable, } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Saglasnost } from '../models/Saglasnost';


@Injectable({
  providedIn: 'root'
})
export class ConsentService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml" });

  constructor(private http: HttpClient) { }

  createConsent(saglasnost: Saglasnost): Observable<HttpResponse<string>> {
    var o2x = require('object-to-xml');
    console.log(o2x(saglasnost));
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.post<HttpResponse<string>>("indirekcija/api/saglasnost/kreirajNovuSaglasnost",
      o2x(saglasnost), queryParams);
  }

  getConsentById(consentId: string): Observable<HttpResponse<string>> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.get<HttpResponse<string>>("indirekcija/api/saglasnost/" + consentId, queryParams);
  }

  updateConsent(saglasnost: Saglasnost | undefined): Observable<HttpResponse<string>> {
    var o2x = require('object-to-xml');
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.put<HttpResponse<string>>("indirekcija/api/saglasnost",
      o2x(saglasnost), queryParams);
  }

  isConsentExist(consentId: string) {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.get<HttpResponse<string>>("indirekcija/api/saglasnost/isConsentExist/" + consentId, queryParams);
  }

  isTerminForConsentExist(email: string): Observable<HttpResponse<string>>  {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.get<HttpResponse<string>>("indirekcija/api/saglasnost/provjeraPostojanjaTerminaZaSaglasnost/" + email, queryParams);
  }
}
