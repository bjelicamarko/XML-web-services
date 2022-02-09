import { AfterViewInit, Component } from '@angular/core';

@Component({
  selector: 'app-header-common',
  templateUrl: './header-common.component.html',
  styleUrls: ['./header-common.component.scss']
})
export class HeaderCommonComponent {

  constructor() { }

  logout() {
    alert("Logouting.....")
  }

  profile() {
    alert("Loading profile data...")
  }
}