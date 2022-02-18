import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MetaSearchService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml", "Accept" : "application/xml"});

  constructor(private http: HttpClient) {}

  advanceSearch(query: string): Observable<HttpResponse<string>> {
    let queryParams = {}
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text",
    };

    return this.http.get<HttpResponse<string>>(`indirekcija/api/metapodaci/pretraga/${query}`, queryParams);
  }

  parseXml(xmlStr: string) : any {
    var result;
    var parser = require('xml2js');
    parser.Parser().parseString(xmlStr, (e: any, r: any) => {result = r});
    return result;
  } 
}
