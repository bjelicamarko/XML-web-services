import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor() { }

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

  public getNoPages(totalItems: number, pageSize: number): number {
    return Math.ceil(totalItems / pageSize);
  }

}
