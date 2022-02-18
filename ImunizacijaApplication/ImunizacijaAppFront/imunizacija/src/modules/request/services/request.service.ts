import { Injectable,  } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Zahtev } from '../models/Zahtev';


@Injectable({
  providedIn: 'root'
})
export class RequestService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml"});

  constructor(private http: HttpClient) {}

  createRequest(zahtev: Zahtev): Observable<HttpResponse<string>> {
    var o2x = require('object-to-xml');
    console.log(o2x(zahtev));
    let queryParams = {};
    queryParams = {
      headers: this.headers, 
      observe: "response",
      responseType: "text"
    };
    return this.http.post<HttpResponse<string>>("indirekcija/api/zahtev/kreirajNovZahtev", o2x(zahtev), queryParams);
  }

  
  canCreateRequest(userId: string): Observable<HttpResponse<string>> {
    let queryParams = {};
    queryParams = {
      headers: this.headers, 
      observe: "response",
      responseType: "text"
    };

    return this.http.get<HttpResponse<string>>(`indirekcija/api/zahtev/canCreateRequest/${userId}`, queryParams);
  }
}
