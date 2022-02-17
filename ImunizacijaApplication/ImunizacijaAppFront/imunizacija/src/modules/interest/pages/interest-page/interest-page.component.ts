import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import * as moment from 'moment';
import { ConformationDialogComponent } from 'src/modules/shared/components/conformation-dialog/conformation-dialog.component';
import { emailValidator } from 'src/modules/shared/directives/custom-validators/email-validator';
import { jmbgValidator } from 'src/modules/shared/directives/custom-validators/jmbg-validator';
import { pasosValidator } from 'src/modules/shared/directives/custom-validators/pasos-validator';
import { Drzavljanstvo } from 'src/modules/shared/models/Drzavljanstvo';
import { Kontakt } from 'src/modules/shared/models/Kontakt';
import { Korisnik } from 'src/modules/shared/models/Korisnik';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UtilService } from 'src/modules/shared/services/util.service';
import { Interesovanje } from '../../models/Interesovanje';
import { Vakcina } from '../../models/Vakcina';
import { InterestService } from '../../services/interest.service';

@Component({
  selector: 'app-interest-page',
  templateUrl: './interest-page.component.html',
  styleUrls: ['./interest-page.component.scss']
})
export class InterestPageComponent {

  registrationFormGroup: FormGroup;

  options: string[] = ['Drzavljanin Republike Srbije',
    'Strani drzavljanin sa boravkom u RS',
    'Strani drzavljanin bez boravka u RS'];
  selectedValue: string = 'Drzavljanin Republike Srbije';
  userIdType: string = 'DOMACE';

  townOptions: string[] = ['Novi Sad', 'Novi Bečej', 'Beograd', 'Subotica'];
  selectedOptionForVaccine: string = 'all';

  checks: Array<string> = ['Pfizer', 'Sputnik V',
    'Sinopharm', 'AstraZeneca', 'Moderna'];

  checkedValues: Array<string> = ['Pfizer', 'Sputnik V',
    'Sinopharm', 'AstraZeneca', 'Moderna'];

  Drzavljanstvo: Drzavljanstvo = {
    "@": {
      Tip: this.userIdType
    }
  };

  Kontakt: Kontakt = {
    'util:Broj_telefona': {
      "#": ''
    },
    'util:Broj_fiksnosg_telefona': {
      "#": ''
    },
    'util:Email_adresa': {
      "#": ''
    }
  }

  korisnik: Korisnik = {
    Korisnik: {
      '@': '',
      'KorisnikID': '',
      'Ime': '',
      'Prezime': '',
      'Email': '',
      'Lozinka': '',
      'TipKorisnika': ''
    }
  };
  
  constructor(public dialog: MatDialog, private fb: FormBuilder, private snackBarService: SnackBarService,
    private interestService: InterestService, private utilService: UtilService) {
    this.registrationFormGroup = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, emailValidator()]],
      phoneNumber: ['', Validators.required],
      homeNumber: [''],
      town: ['Novi Sad', Validators.required],
      userId: ['', [Validators.required, jmbgValidator()]],
      donator: ['Ne', Validators.required]
    });
  }

  get f() {
    return this.registrationFormGroup.controls;
  }

  createInterestWithConformation() {
    this.dialog.open(ConformationDialogComponent, {
      data:
      {
        naslov: "Podnosenje interesovanja",
        poruka: "Jeste sigurni da zelite da podnesete novi zahtev za vakcinisanje."
      },
    }).afterClosed().subscribe(result => {
      if (result) {
        let userId = this.utilService.getLoggedUserID();
        this.utilService.getUser(userId + ".xml")
          .subscribe(response => {
            if (response.body)
              this.korisnik = this.utilService.parseXml(response.body);
              this.interestService.isInterestExist(this.korisnik.Korisnik.Email)
              .subscribe(response => {
                if (response.body === "Nepostoji interesovanje.") {
                  this.createInterest();
                } else {
                  this.snackBarService.openSnackBar(response.body as string);
                }
              })
          })
      }
    });
  }

  createInterest() {
    if (this.checkedValues.length === 0) {
      this.snackBarService.openSnackBar("Morate izabrati bar jednu vakcinu.");
      return;
    }

    if (this.selectedValue === 'Drzavljanin Republike Srbije') {
      this.Drzavljanstvo['util:JMBG'] = this.registrationFormGroup.get('userId')?.value;
    } else if (this.selectedValue === 'Strani drzavljanin sa boravkom u RS') {
      this.Drzavljanstvo['util:Evidencioni_broj_stranca'] = this.registrationFormGroup.get('userId')?.value;
    } else if (this.selectedValue === 'Strani drzavljanin bez boravka u RS') {
      this.Drzavljanstvo['util:Br_pasosa'] = this.registrationFormGroup.get('userId')?.value;
    }

    if (this.registrationFormGroup.get('homeNumber')?.value === '')
      this.Kontakt['util:Broj_fiksnosg_telefona']['#'] = '08005775';
    else
      this.Kontakt['util:Broj_fiksnosg_telefona']['#'] = this.registrationFormGroup.get('homeNumber')?.value;
    this.Kontakt['util:Broj_telefona']['#'] = this.registrationFormGroup.get('phoneNumber')?.value;
    this.Kontakt['util:Email_adresa']['#'] = this.registrationFormGroup.get('email')?.value;

    let arrayOfVaccine: Vakcina[] = [];
    this.checkedValues.forEach((value) => {
      arrayOfVaccine.push({ "@": { Tip: value } })
    })

    this.interestService.createInterest(this.getNewInterest(arrayOfVaccine))
      .subscribe(response => {
        this.snackBarService.openSnackBar(response.body as string);
        this.resetStateOfForm();
      });

  }

  getNewInterest(arrayOfVaccine: Vakcina[]): Interesovanje {
    let interesovanje: Interesovanje = {
      Interesovanje:
      {
        "@": {
          "xmlns": "http://www.vakc-sistem.rs/interesovanje",
          "xmlns:xsi": "http://www.w3.org/2001/XMLSchema-instance",
          "xmlns:util": "http://www.vakc-sistem.rs/util",
          "xsi:schemaLocation": "http://www.vakc-sistem.rs/interesovanje interesovanje.xsd",
          Id: "1"
        },
        Drzavljanstvo: this.Drzavljanstvo,
        Ime: this.registrationFormGroup.get('firstName')?.value,
        Prezime: this.registrationFormGroup.get('lastName')?.value,
        Kontakt: this.Kontakt,
        Opstina_vakcinisanja: this.registrationFormGroup.get('town')?.value,
        Vakcina: arrayOfVaccine,
        Dobrovoljni_davalac: this.registrationFormGroup.get('donator')?.value,
        Datum: moment(Date.now()).format('YYYY-MM-DD')
      }
    };
    return interesovanje;
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

  handleChange(_any: any) {
    this.selectedOptionForVaccine = _any.target.value;
    if (this.selectedOptionForVaccine === 'all') {
      this.resetCheckedValues();
    } else {  // 'specific' case
      this.checkedValues = [];
    }
  }

  onCheckChange(_any: any) {
    if (_any.target.checked) {
      this.checkedValues.push(_any.target.value);
    } else {
      this.removeCheckedValue(_any.target.value);
    }
  }

  resetCheckedValues() {
    this.checkedValues = [];
    this.checks.forEach((value) => {
      this.checkedValues.push(value);
    });
  }

  removeCheckedValue(value: string) {
    const index: number = this.checkedValues.indexOf(value);
    if (index !== -1) {
      this.checkedValues.splice(index, 1);
    }
  }

  resetStateOfForm() {
    this.registrationFormGroup = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, emailValidator()]],
      phoneNumber: ['', Validators.required],
      homeNumber: [''],
      town: ['Novi Sad', Validators.required],
      userId: ['', [Validators.required, jmbgValidator()]],
      donator: ['Da', Validators.required]
    });
    this.resetCheckedValues();
  }
}
