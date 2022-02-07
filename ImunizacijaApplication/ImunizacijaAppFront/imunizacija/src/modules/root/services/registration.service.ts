import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseService } from 'src/modules/shared/services/base.service';
import { RegistrationData } from '../models/registrationData';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService extends BaseService {

  constructor(private http: HttpClient) {
    super();
  }

  registerUser(registrationData: RegistrationData): Observable<HttpResponse<String>> {
    return this.http.post<HttpResponse<String>>("imunizacija-app/users/registration", registrationData, this.generateQueryParamsWithText());
  }
}
