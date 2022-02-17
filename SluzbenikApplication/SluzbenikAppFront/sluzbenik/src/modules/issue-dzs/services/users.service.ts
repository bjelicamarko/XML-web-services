import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDocsDTO } from '../models/user-docs-dto';
import { UserViewDTO } from '../models/user-view-dto';
import { UsersViewDTOList } from '../models/user-view-dto-list';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml" });

  constructor(private http: HttpClient) { }

  getAll(): Observable<HttpResponse<string>> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.get<HttpResponse<string>>("indirekcija/api/users/", queryParams);
  }

  getOne(id: string): Observable<HttpResponse<string>> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.get<HttpResponse<string>>("indirekcija/api/users/" + id, queryParams);
  }

  approveDZS(zahtevID: string, userId: string, userEmail: string): Observable<HttpResponse<string>> {
    var o2x = require('object-to-xml');
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    let reqObj = { "zahtev": { "zahtevId": zahtevID, "userId": userId, "userEmail": userEmail } }
    return this.http.post<HttpResponse<string>>("indirekcija/api/dzs/", o2x(reqObj), queryParams);
  }

  declineDZS(zahtevID: string, reason: string, userEmail: string): Observable<HttpResponse<string>> {
    var o2x = require('object-to-xml');
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    let reqObj = { "odbijanje": { "zahtevId": zahtevID, "reason": reason, "userEmail": userEmail } }
    return this.http.post<HttpResponse<string>>("indirekcija/api/dzs/odbijanje-zahteva", o2x(reqObj), queryParams);
  }

  getDocumentationOfUser(userID: string): Observable<HttpResponse<string>> {
    let queryParams = {};
    queryParams = {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    };
    return this.http.get<HttpResponse<string>>("indirekcija/api/users/dokumentacija/" + userID, queryParams);
  }

  usersRespfromXMLToObj(xml: string): UsersViewDTOList {
    var xml2js = require('xml2js');
    var parser = new xml2js.Parser({ explicitArray: false });
    var resp: UsersViewDTOList = { users: { korisnikBasicInfoDTOList: [] } };
    parser.parseString(xml, function (err: any, result: any) {
      resp = result as UsersViewDTOList;
    });
    return resp;
  }

  userRespfromXMLToObj(xml: string): UserViewDTO {
    var xml2js = require('xml2js');
    var parser = new xml2js.Parser({ explicitArray: false });
    var resp: UserViewDTO = { email: "", firstName: "", lastName: "", userID: "" };
    parser.parseString(xml, function (err: any, result: any) {
      resp = result.user as UserViewDTO;
    });
    return resp;
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
