import { Injectable,  } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Interesovanje } from '../models/Interesovanje';

@Injectable({
  providedIn: 'root'
})
export class InterestService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml"});

  constructor(private http: HttpClient) {}

  createInterest(interesovanje: Interesovanje): Observable<HttpResponse<string>> {
    var o2x = require('object-to-xml');
    console.log(o2x(interesovanje));
    let queryParams = {};
    queryParams = {
      headers: this.headers, 
      observe: "response",
      responseType: "text"
    };
    return this.http.post<HttpResponse<string>>("indirekcija/api/interesovanje/kreirajNovoInteresovanje", 
    o2x(interesovanje), queryParams);
  }
}
