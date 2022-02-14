import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseService } from 'src/modules/shared/services/base.service';
import { RegistrationData } from '../models/registrationData';
import { UserRegistrationDTO } from '../models/userRegistrationDTO';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml" });

  constructor(private http: HttpClient) { }

  registerUser(userdto: UserRegistrationDTO): Observable<HttpResponse<string>> {
    var o2x = require('object-to-xml');
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.post<HttpResponse<string>>("indirekcija/api/users/registracija", o2x(userdto), queryParams);
  }

  // registerUser(registrationData: RegistrationData): Observable<HttpResponse<String>> {
  //   return this.http.post<HttpResponse<String>>("imunizacija-app/users/registration", registrationData, this.generateQueryParamsWithText());
  // }
}
