import { Injectable,  } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ConsentService {

  private headers = new HttpHeaders({ "Content-Type": "application/xml"});

  constructor(private http: HttpClient) {}
}
