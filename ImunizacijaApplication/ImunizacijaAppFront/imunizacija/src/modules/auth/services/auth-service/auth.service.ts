import { Injectable } from "@angular/core";
import { HttpHeaders, HttpClient, HttpResponse } from "@angular/common/http";
import { Observable } from "rxjs";
import { Login } from "src/modules/shared/models/login";
import { Token } from "src/modules/shared/models/token";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private headers = new HttpHeaders({ "Content-Type": "application/xml" });

  constructor(private http: HttpClient) { }

  login(auth: Login): Observable<HttpResponse<string>> {
    var o2x = require('object-to-xml');
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.post<HttpResponse<string>>("indirekcija/api/users/login", o2x(auth), queryParams);
  }

  tokenRespfromXMLToJSON(xml: string): string {
    var xml2js = require('xml2js');
    var parser = new xml2js.Parser({ explicitArray: false });
    var resp = "";
    parser.parseString(xml, function (err: any, result: any) {
      resp = JSON.stringify(result.token_state);
    });
    return resp;
  }

  logout(): void {
    localStorage.removeItem("user");
  }

  isLoggedIn(): boolean {
    if (!localStorage.getItem("user")) {
      return false;
    }
    return true;
  }
}
