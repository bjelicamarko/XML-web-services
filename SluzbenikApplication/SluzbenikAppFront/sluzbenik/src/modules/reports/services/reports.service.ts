import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportsService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(private http: HttpClient) {}

  getReports(dateFrom: string, dateTo: string): Observable<HttpResponse <string>> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };

   return this.http.get<HttpResponse <string>>
       (`indirekcija/api/izvestaji/dobaviIzvestaje/${dateFrom}&${dateTo}`, queryParams);

    //return this.http.get<HttpResponse <string>>
        //(`indirekcija/api/izvestaji/dobaviIzvestaje/2022-01-07&2022-01-12`, queryParams);
  }
}
