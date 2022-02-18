import { Injectable } from "@angular/core";
import { Router, CanActivate } from "@angular/router";
import { UtilService } from "src/modules/shared/services/util.service";
import { AuthService } from "../../services/auth-service/auth.service";

@Injectable({
  providedIn: "root",
})
export class LoginGuard implements CanActivate {
  constructor(public auth: AuthService, public router: Router, public utilsService: UtilService) { }

  canActivate(): boolean {
    if (this.auth.isLoggedIn()) {  // TODO da redirect na home page
      let role = this.utilsService.getLoggedUserRole();

      //todo srediti
      if (role === "CITIZEN")
        this.router.navigate(["imunizacija-app/interesovanje/novo-interesovanje"]);
      if (role === "DOCTOR")
        this.router.navigate(["imunizacija-app/saglasnost/drugi-deo-saglasnosti"]);
      // else if (role === "MANAGER") {
      //   this.router.navigate(["rest-app/employees/employees-manager"]);
      // }
      // else if (role === "COOK") {
      //   this.router.navigate(["rest-app/orders/orders-page"]);
      // }
      // else if (role === "HEAD_COOK") {
      //   this.router.navigate(["rest-app/orders/orders-page"]);
      // }
      // else if (role === "BARMAN") {
      //   this.router.navigate(["rest-app/orders/orders-page"]);
      // }
      // else if (role === "WAITER") {
      //   this.router.navigate(["rest-app/tables/tables-waiter"]);
      // }
      // return false;
      return false;
    }
    return true;
  }
}
