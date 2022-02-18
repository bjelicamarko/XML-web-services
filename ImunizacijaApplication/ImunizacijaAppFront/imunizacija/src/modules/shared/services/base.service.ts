import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BaseService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml" });

  constructor() { }

  generateQueryParams(): object {
    return {
      headers: this.headers,
      observe: "response"
    }
  }

  generateQueryParamsWithText(): object {
    return {
      headers: this.headers,
      observe: "response",
      responseType: "text"
    }
  }
}
