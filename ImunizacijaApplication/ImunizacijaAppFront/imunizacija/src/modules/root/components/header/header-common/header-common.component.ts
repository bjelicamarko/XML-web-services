import { AfterViewInit, Component } from '@angular/core';
import { AuthService } from 'src/modules/auth/services/auth-service/auth.service';

@Component({
  selector: 'app-header-common',
  templateUrl: './header-common.component.html',
  styleUrls: ['./header-common.component.scss']
})
export class HeaderCommonComponent {

  constructor(private authService: AuthService) { }

  logout() {
    this.authService.logout();
  }

  profile() {
    alert("Loading profile data...")
  }
}