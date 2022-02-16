import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { Korisnik } from '../models/Korisnik';

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

  parseXml(xmlStr: string): any {
    var result;
    var parser = require('xml2js');
    parser.Parser().parseString(xmlStr, (e: any, r: any) => { result = r });
    return result;
  }
}
