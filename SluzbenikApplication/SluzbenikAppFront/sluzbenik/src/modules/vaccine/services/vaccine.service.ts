import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { GradVakcinaKolicinaDTO } from '../models/GradVakcinaKolicinaDTO';

@Injectable({
  providedIn: 'root'
})
export class VaccineService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml", "Accept" : "application/xml"});

  constructor(private http: HttpClient) {}

  getVaccineStatusOfCity(city: string): Observable<HttpResponse<string>> {
    let queryParams = {}
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text",
    };

    return this.http.get<HttpResponse<string>>(
      `indirekcija/api/sistemski-magacin/dobaviStanjeVakcinaGrada/${city}`, queryParams);
  }

  updateVaccine(gradVakcinaKolicinaDTO: GradVakcinaKolicinaDTO): Observable<HttpResponse<string>> {
    var o2x = require('object-to-xml');
    let queryParams = {}
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text",
    };

    return this.http.put<HttpResponse<string>>("indirekcija/api/sistemski-magacin/azurirajVakcinu", 
    o2x(gradVakcinaKolicinaDTO), queryParams);
  }

  parseXml(xmlStr: string) : any {
    var result;
    var parser = require('xml2js');
    parser.Parser().parseString(xmlStr, (e: any, r: any) => {result = r});
    return result;
  } 
}
