import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { timingSafeEqual } from 'crypto';
import * as moment from 'moment';
import { ConformationDialogComponent } from 'src/modules/shared/components/conformation-dialog/conformation-dialog.component';
import { emailValidator } from 'src/modules/shared/directives/custom-validators/email-validator';
import { jmbgValidator } from 'src/modules/shared/directives/custom-validators/jmbg-validator';
import { pasosValidator } from 'src/modules/shared/directives/custom-validators/pasos-validator';
import { Drzavljanstvo } from 'src/modules/shared/models/Drzavljanstvo';
import { Kontakt } from 'src/modules/shared/models/Kontakt';
import { LicniPodaci } from 'src/modules/shared/models/LicniPodaci';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { Saglasnost } from '../../models/Saglasnost';
import { ConsentService } from '../../services/consent.service';

@Component({
  selector: 'app-consent-page',
  templateUrl: './consent-page.component.html',
  styleUrls: ['./consent-page.component.scss']
})
export class ConsentPageComponent {

  registrationFormGroup: FormGroup;
  
  options: string[] = ['Drzavljanin Republike Srbije', 
        'Strani drzavljanin sa boravkom u RS', 
        'Strani drzavljanin bez boravka u RS'];
  selectedValue: string = 'Drzavljanin Republike Srbije';
  userIdType: string = 'DOMACE';

  LicniPodaci: LicniPodaci = {
    Ime: {
      "@": { xmlns:"http://www.vakc-sistem.rs/util" },
      "#": ''
    },
    Prezime: {
      "@": { xmlns:"http://www.vakc-sistem.rs/util" },
      '#': ''
    },
    Datum_rodjenja: {
      "@": { xmlns:"http://www.vakc-sistem.rs/util" },
      "#": ''
    },
    Pol: {
      "@": { xmlns:"http://www.vakc-sistem.rs/util" },
      "#": ''
    },
    Ime_roditelja: {
      "@": { xmlns:"http://www.vakc-sistem.rs/util" },
      "#": ''
    },
    Mesto_rodjenja: {
      "@": { xmlns:"http://www.vakc-sistem.rs/util" },
      "#": ''
    },
    Adresa: {
      "@": { xmlns:"http://www.vakc-sistem.rs/util" },
      Ulica: { Naziv: '', Broj: ''},
      Naselje: '',
      Mesto: '',
      Opstina: '',
      Grad: '',
    }
  }

  Drzavljanstvo: Drzavljanstvo = {
    "@": {
      Tip: this.userIdType
    }
  };
  
  Kontakt : Kontakt = {
    Broj_telefona: {
      "@": {
        xmlns: 'http://www.vakc-sistem.rs/util'
      },
      "#": ''
    },
    Broj_fiksnosg_telefona: {
      "@": {
        xmlns: 'http://www.vakc-sistem.rs/util'
      },
      "#": ''
    },
    Email_adresa: {
      "@": {
        xmlns: 'http://www.vakc-sistem.rs/util'
      },
      "#": ''
    }
  }
  
  jobs: Array<string> = ['Zdravstvena zastita', 'Socijalna zastita', 
  'Prosveta', 'MUP', 'Vojska RS', 'Drugo'];

  constructor(public dialog: MatDialog, private fb: FormBuilder,  private snackBarService: SnackBarService,
    private consentService: ConsentService) { 
      this.registrationFormGroup = this.fb.group({
        userId: ['', [Validators.required, jmbgValidator()]],
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        birthDay: [Validators.required],
        gender: ['Musko', Validators.required],
        parentName: ['', Validators.required],
        birthPlace: ['', Validators.required],
        streetName: ['', Validators.required],
        streetNumber: ['', Validators.required],
        place: ['', Validators.required], // mesto
        city: ['', Validators.required],
        email: ['', [Validators.required, emailValidator()]],
        phoneNumber: ['', Validators.required],
        homeNumber: [''], // fiksni telefon
        workStatus: ['', Validators.required],
        interest: ['Drugo', Validators.required],
        agreed: ['Ne', Validators.required],        
        immunologicalDrug: ['', Validators.required]
      });
    }

    get f() {
      return this.registrationFormGroup.controls;
    }

    createConsentWithConformation() {
      this.dialog.open(ConformationDialogComponent, {
        data: 
        { 
          naslov: "Podnosenje saglasnosti",
          poruka: "Jeste sigurni da zelite da sprovedete saglasnost za imunizaciju."
        },
      }).afterClosed().subscribe(result => {
        if (result) {
          this.createConsent();
        }
      });
    }

    createConsent() {
      if (this.selectedValue === 'Drzavljanin Republike Srbije') {  
        this.Drzavljanstvo['util:JMBG'] = this.registrationFormGroup.get('userId')?.value;
      } else if (this.selectedValue === 'Strani drzavljanin sa boravkom u RS') {
        this.Drzavljanstvo['util:Evidencioni_broj_stranca'] = this.registrationFormGroup.get('userId')?.value;
      } else if (this.selectedValue === 'Strani drzavljanin bez boravka u RS') {
        this.Drzavljanstvo['util:Br_pasosa'] = this.registrationFormGroup.get('userId')?.value;
      }

      this.Kontakt.Broj_fiksnosg_telefona['#'] = this.registrationFormGroup.get('homeNumber')?.value;
      this.Kontakt.Broj_telefona['#'] = this.registrationFormGroup.get('phoneNumber')?.value;
      this.Kontakt.Email_adresa['#'] = this.registrationFormGroup.get('email')?.value;

      this.LicniPodaci.Ime['#'] = this.registrationFormGroup.get('firstName')?.value;
      this.LicniPodaci.Prezime['#'] = this.registrationFormGroup.get('lastName')?.value;
      this.LicniPodaci.Datum_rodjenja['#'] = moment(this.registrationFormGroup.get('birthDay')?.value).format('YYYY-MM-DD');
      this.LicniPodaci.Pol['#'] = this.registrationFormGroup.get('gender')?.value;
      this.LicniPodaci.Ime_roditelja['#'] = this.registrationFormGroup.get('parentName')?.value;
      this.LicniPodaci.Mesto_rodjenja['#'] = this.registrationFormGroup.get('birthPlace')?.value;
      this.LicniPodaci.Adresa.Ulica.Naziv = this.registrationFormGroup.get('streetName')?.value;
      this.LicniPodaci.Adresa.Ulica.Broj = this.registrationFormGroup.get('streetNumber')?.value;
      this.LicniPodaci.Adresa.Naselje = this.registrationFormGroup.get('place')?.value;
      this.LicniPodaci.Adresa.Mesto = this.registrationFormGroup.get('place')?.value;
      this.LicniPodaci.Adresa.Opstina = this.registrationFormGroup.get('city')?.value;
      this.LicniPodaci.Adresa.Grad = this.registrationFormGroup.get('city')?.value;

    }

    getNewConsent(): Saglasnost {
      let saglasnost: Saglasnost = {
        Saglasnost: {
          "@": {
            "xmlns": "http://www.vakc-sistem.rs/saglasnost-za-imunizaciju",
            "xmlns:xsi": "http://www.w3.org/2001/XMLSchema-instance",
            "xmlns:util": "http://www.vakc-sistem.rs/util",
            "xsi:schemaLocation": "http://www.vakc-sistem.rs/saglasnost-za-imunizaciju saglasnost_za_imunizaciju.xsd",
            Id: "-1"
          },
          Drzavljanstvo: this.Drzavljanstvo,
          Licni_podaci: this.LicniPodaci,
          Kontakt: this.Kontakt,
          Zaposlenje: { 
            Radni_status: this.registrationFormGroup.get('workStatus')?.value, 
            Zanimanje: this.registrationFormGroup.get('interest')?.value
          },
          Izjava: { 
            Saglasan: this.registrationFormGroup.get('agreed')?.value, 
            Imunoloski_lek: this.registrationFormGroup.get('immunologicalDrug')?.value 
          },
          Datum: moment(Date.now()).format('YYYY-MM-DD')
        }
      }
      return saglasnost;
    }

    checkSubmit() {
      return !this.registrationFormGroup.valid;
    }

    onChange(_any: any) {
      this.selectedValue = _any;
      if (this.selectedValue === 'Drzavljanin Republike Srbije') {  
        this.registrationFormGroup.get('userId')?.setValidators(Validators.compose([Validators.required, jmbgValidator()]));
        this.userIdType = 'DOMACE';
      } else if (this.selectedValue === 'Strani drzavljanin sa boravkom u RS') {
        this.registrationFormGroup.get('userId')?.setValidators(Validators.compose([Validators.required]));
        this.userIdType = 'STRANO_SA_BORAVKOM';
      } else if (this.selectedValue === 'Strani drzavljanin bez boravka u RS') {
        this.registrationFormGroup.get('userId')?.setValidators(Validators.compose([Validators.required, pasosValidator()]));
        this.userIdType = 'STRANO_BEZ_BORAVKA';
      }
      this.registrationFormGroup.get('userId')?.setValue('');
      this.checkSubmit();
    }

    resetStateOfForm() {
      this.registrationFormGroup = this.fb.group({
        userId: ['', [Validators.required, jmbgValidator()]],
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        birthDay: ['', Validators.required],
        gender: ['', Validators.required],
        parentName: ['', Validators.required],
        birthPlace: ['', Validators.required],
        streetName: ['', Validators.required],
        streetNumber: ['', Validators.required],
        place: ['', Validators.required], // mesto
        city: ['', Validators.required],
        email: ['', [Validators.required, emailValidator()]],
        phoneNumber: ['', Validators.required],
        homeNumber: [''], // fiksni telefon
        workStatus: ['', Validators.required],
        interest: ['', Validators.required],
        agreed: ['', Validators.required],        
        immunologicalDrug: ['', Validators.required]
      });
    }
}
