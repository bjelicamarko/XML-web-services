import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';
import { jmbgValidator } from 'src/modules/shared/directives/custom-validators/jmbg-validator';
import { pasosValidator } from 'src/modules/shared/directives/custom-validators/pasos-validator';
import { DEFAULT_IDS } from 'src/modules/shared/enums/deafult-identifiers';
import { LicniPodaciJmbgBrPasosa } from 'src/modules/shared/models/LicniPodaci';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { Zahtev } from '../../models/Zahtev';
import { RequestService } from '../../services/request.service';

@Component({
  selector: 'app-request-page',
  templateUrl: './request-page.component.html',
  styleUrls: ['./request-page.component.scss']
})
export class RequestPageComponent implements OnInit {
  
  public tools: object = {
    items: ['Undo', 'Redo', '|',
        'Bold', 'Italic']
  };
  public iframe: object = { enable: true };
  public height: number = 200;

  registrationFormGroup: FormGroup;

  genderOptions: string[];
  options: string[];
  selectedValue: string;
  userIdType: string;

  Podnosilac: LicniPodaciJmbgBrPasosa = {
    Ime: {
      '@': {xmlns: 'http://www.vakc-sistem.rs/util'},
      '#': ''
    },
    Prezime: {
      '@': {xmlns: 'http://www.vakc-sistem.rs/util'},
      '#': ''
    },
    Datum_rodjenja: {
      '@': {xmlns: 'http://www.vakc-sistem.rs/util'},
      '#': ''
    },
    Pol: {
      '@': {xmlns: 'http://www.vakc-sistem.rs/util'},
      '#': ''
    },
    JMBG: {
      '@': {xmlns: 'http://www.vakc-sistem.rs/util'},
      '#': ''
    },
    Broj_pasosa: {
      '@': {xmlns: 'http://www.vakc-sistem.rs/util'},
      '#': ''
    }
  };

  constructor(private fb: FormBuilder, private requestService: RequestService,
              private snackBarService: SnackBarService) { 
    this.genderOptions = ['Muski', 'Zenski'];
    this.options = ['Drzavljanin Republike Srbije', 'Strani drzavljanin bez boravka u RS'];
    this.selectedValue = 'Drzavljanin Republike Srbije';
    this.userIdType = 'DOMACE';

    this.registrationFormGroup = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthDate: ['', Validators.required],
      gender: ['Musko', Validators.required],
      userId: ['', [Validators.required, jmbgValidator()]],
      reason: ['', Validators.required],
      place: ['Novi Sad', Validators.required],
      date: ['Da', Validators.required]
    });

  }

  ngOnInit(): void {
  }

  get f() {
    return this.registrationFormGroup.controls;
  }

  checkSubmit() {
    return !this.registrationFormGroup.valid;
  }

  onChange(_any: any) {
    this.selectedValue = _any;
    if (this.selectedValue === 'Drzavljanin Republike Srbije') {  
      this.registrationFormGroup.get('userId')?.setValidators(Validators.compose([Validators.required, jmbgValidator()]));
      this.userIdType = 'DOMACE';
    }  else if (this.selectedValue === 'Strani drzavljanin bez boravka u RS') {
      this.registrationFormGroup.get('userId')?.setValidators(Validators.compose([Validators.required, pasosValidator()]));
      this.userIdType = 'STRANO_BEZ_BORAVKA';
    }

    this.registrationFormGroup.get('userId')?.setValue('');
    this.checkSubmit();
  }

  createRequest() {
    if (this.selectedValue === 'Drzavljanin Republike Srbije') {  
      this.Podnosilac.JMBG['#'] = this.registrationFormGroup.get('userId')?.value;
      this.Podnosilac.Broj_pasosa['#'] = DEFAULT_IDS.BR_PASOSA;
    } else if (this.selectedValue === 'Strani drzavljanin bez boravka u RS') {
      this.Podnosilac.Broj_pasosa['#'] = this.registrationFormGroup.get('userId')?.value;
      this.Podnosilac.JMBG['#'] = DEFAULT_IDS.JMBG;
    }

    this.Podnosilac.Ime['#'] = this.registrationFormGroup.get('firstName')?.value;
    this.Podnosilac.Prezime['#'] = this.registrationFormGroup.get('lastName')?.value;
    this.Podnosilac.Datum_rodjenja['#'] = moment(this.registrationFormGroup.get('birthDate')?.value).format('YYYY-MM-DD');
    this.Podnosilac.Pol['#'] = this.registrationFormGroup.get('gender')?.value;

    let newRequest: Zahtev = this.createNewRequest();
    
    this.requestService.createRequest(newRequest).subscribe((response) => {
      this.snackBarService.openSnackBar(response.body as string);
          this.resetStateOfForm();
    });
  }

  createNewRequest() : Zahtev {
    let zahtev: Zahtev = {
      Zahtev: {
        "@": {
          "xmlns" : "http://www.vakc-sistem.rs/zahtev-dzs",
          "xmlns:xsi": "http://www.w3.org/2001/XMLSchema-instance",
          "xsi:schemaLocation": "http://www.vakc-sistem.rs/zahtev-dzs zahtev_za_izdavanje_zelenog.xsd",
          Id: "1"
        },
        Podnosilac: this.Podnosilac,
        Razlog: this.registrationFormGroup.get('reason')?.value,
        Mesto: this.registrationFormGroup.get('place')?.value,
        Datum: moment(Date.now()).format('YYYY-MM-DD')
      }
    };

    return zahtev;
  }

  private resetStateOfForm() {
    this.registrationFormGroup = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthDate: ['', Validators.required],
      gender: ['Musko', Validators.required],
      userId: ['', [Validators.required, jmbgValidator()]],
      reason: ['', Validators.required],
      place: ['Novi Sad', Validators.required],
      date: ['Da', Validators.required]
    });
  }

}
