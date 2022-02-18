import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDocsDTO } from '../models/user-docs-dto';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml" });

  constructor(private http: HttpClient) { }

  getDocumentationOfUser(userID: string): Observable<HttpResponse<string>> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.get<HttpResponse<string>>("indirekcija/api/users/dokumentacija_za_korisnika/" + userID, queryParams);
  }

  usersDocRespfromXMLToObj(xml: string): UserDocsDTO {
    var xml2js = require('xml2js');
    var parser = new xml2js.Parser();
    var resp: UserDocsDTO = {
      documentsOfUser: {
        dzsList: [], interesovanjeID: [],
        potvrdaOVakcList: [], salgasnostList: [], zahtevDZSList: [], prihvaceniZahtevDZSList: []
      }
    };
    parser.parseString(xml, function (err: any, result: any) {
      if (result['documentsOfUser'].hasOwnProperty("dzsList")) {
        resp.documentsOfUser.dzsList = result['documentsOfUser'].dzsList;
      }
      if (result['documentsOfUser'].hasOwnProperty("interesovanjeID")) {
        resp.documentsOfUser.interesovanjeID = result['documentsOfUser'].interesovanjeID;
      }
      if (result['documentsOfUser'].hasOwnProperty("potvrdaOVakcList")) {
        resp.documentsOfUser.potvrdaOVakcList = result['documentsOfUser'].potvrdaOVakcList;
      }
      if (result['documentsOfUser'].hasOwnProperty("salgasnostList")) {
        resp.documentsOfUser.salgasnostList = result['documentsOfUser'].salgasnostList;
      }
      if (result['documentsOfUser'].hasOwnProperty("zahtevDZSList")) {
        resp.documentsOfUser.zahtevDZSList = result['documentsOfUser'].zahtevDZSList;
      }

      if (result['documentsOfUser'].hasOwnProperty("prihvaceniZahtevDZSList")) {
        resp.documentsOfUser.prihvaceniZahtevDZSList = result['documentsOfUser'].prihvaceniZahtevDZSList;
      }
    });
    return resp;
  }
}
