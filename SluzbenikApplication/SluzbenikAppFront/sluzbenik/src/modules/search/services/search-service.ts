import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml", "Accept": "application/xml" });

  constructor(private http: HttpClient) { }

  basicSearch(userId: string, searchText: string, documentType: string): Observable<HttpResponse<string>> {
    let queryParams = {}
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text",
    };

    return this.http.get<HttpResponse<string>>(`indirekcija/api/${documentType}/search?userId=${userId}&searchText=${searchText}`, queryParams);
  }

  parseXml(xmlStr: string): any { // { explicitArray: false }
    var result;
    var parser = require('xml2js');
    parser.Parser().parseString(xmlStr, (e: any, r: any) => { result = r });
    return result;
  }

  parseXmlArrayFalse(xmlStr: string): any { // { explicitArray: false }
    var result;
    var parser = require('xml2js');
    parser.Parser({ explicitArray: false }).parseString(xmlStr, (e: any, r: any) => { result = r });
    return result;
  }

  returnDummy2(): string {
    return `<Search_results>
    <Search_result>
        <Document_id>4125125</Document_id>
        <Referencing>
            <Document_id>2312312</Document_id>
        </Referencing>
        <Referenced_by>
            <Document_id>3</Document_id>
        </Referenced_by>
    </Search_result>
    <Search_result>
        <Document_id>7654321</Document_id>
        <Referencing/>
        <Referenced_by>
            <Document_id>3</Document_id>
        </Referenced_by>
    </Search_result>
  </Search_results>`;
  }

  returnDumy(): string {
    return `
    <Search_results>
        <Search_result>
            <Document_id>4125125</Document_id>
            <Referencing>
                <Document_id>Milan</Document_id>
                <Document_id>Cvetko</Document_id>
                <Document_id>Drago</Document_id>
            </Referencing>
            <Referenced_by>
                <Document_id>Pero</Document_id>
                <Document_id>Zdero</Document_id>
                <Document_id>Gvero</Document_id>
            </Referenced_by>
        </Search_result>
        <Search_result>
            <Document_id>7654321</Document_id>
            <Referencing>
                <Document_id>Djura</Document_id>
                <Document_id>Mika</Document_id>
                <Document_id>Zika</Document_id>
            </Referencing>
            <Referenced_by>
                <Document_id>Lane</Document_id>
                <Document_id>Pane</Document_id>
                <Document_id>Dane</Document_id>
            </Referenced_by>
        </Search_result>
    </Search_results>
    `;
  }

}
