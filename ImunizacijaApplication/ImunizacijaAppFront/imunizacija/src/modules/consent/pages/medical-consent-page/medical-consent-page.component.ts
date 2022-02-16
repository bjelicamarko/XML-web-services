import { TOUCH_BUFFER_MS } from '@angular/cdk/a11y/input-modality/input-modality-detector';
import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { stringify } from 'querystring';
import { ConnectableObservable } from 'rxjs';
import { ConformationDialogComponent } from 'src/modules/shared/components/conformation-dialog/conformation-dialog.component';
import { emailValidator } from 'src/modules/shared/directives/custom-validators/email-validator';
import { jmbgValidator } from 'src/modules/shared/directives/custom-validators/jmbg-validator';
import { Doza, Doza_detaljnije } from 'src/modules/shared/models/Doza';
import { Drzavljanstvo } from 'src/modules/shared/models/Drzavljanstvo';
import { Kontraindikacija, Kontraindikacije } from 'src/modules/shared/models/Kontraindikacije';
import { Korisnik } from 'src/modules/shared/models/Korisnik';
import { PodaciOLekaru } from 'src/modules/shared/models/PodaciOLekaru';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { UtilService } from 'src/modules/shared/services/util.service';
import { Saglasnost } from '../../models/Saglasnost';
import { ConsentService } from '../../services/consent.service';

@Component({
  selector: 'app-medical-consent-page',
  templateUrl: './medical-consent-page.component.html',
  styleUrls: ['./medical-consent-page.component.scss']
})
export class MedicalConsentPageComponent {

  consentFormGroup: FormGroup;
  ekstremiteti: string[] = ["LR", "DR"]
  ekstremitet: string;
  consentToUpdate: Saglasnost | undefined;
  korisnik: Korisnik | undefined;

  Podaci_o_lekaru: PodaciOLekaru = {
    Ime: '',
    Prezime: '',
    Faksimil: '',
    Broj_telefona: '',
    JMBG: ''
  }

  Doza: Doza_detaljnije = {
    "@": {
      Redni_broj: 0
    },
    'util:Datum': {
      "#": ''
    },
    'util:Serija': {
      "#": ''
    },
    'util:Proizvodjac': {
      "#": ''
    },
    'util:Tip': {
      "#": ''
    },
    'util:Ekstremitet': {
      "#": ''
    },
    'util:Nezeljena_rekacija': {
      "#": ''
    }
  }

  Kontraindikacije: Kontraindikacije = {
    Privremena_kontraindikacija: [],
    Odluka_komisije_o_trajnim: ''
  }

  Kontraindikacija: Kontraindikacija = {
    Datum: '',
    Dijagnoza: ''
  }

  Drzavljanstvo: Drzavljanstvo = {
    "@": {
      Tip: ''
    }
  };

  constructor(
    public dialog: MatDialog,
    private router: Router,
    private fb: FormBuilder,
    private consentService: ConsentService,
    private snackBarService: SnackBarService,
    private utilService: UtilService
  ) {
    this.consentService.getConsentById("7654321.xml")
      .subscribe(response => {
        if (response.body)
          this.consentToUpdate = this.utilService.parseXml(response.body);
      })
    let userId = this.utilService.getLoggedUserID();
    this.utilService.getUser(userId + ".xml")
      .subscribe(response => {
        if (response.body)
          this.korisnik = this.utilService.parseXml(response.body);
      })
    this.ekstremitet = this.ekstremiteti[0];
    this.consentFormGroup = this.fb.group({
      zdravstvenaUstanova: ['', [Validators.required]],
      punkt: ['', Validators.required],
      faksimil: ['', Validators.required],
      telefon: ['', Validators.required],
      nezeljenaReakcija: [''],
      dijagnoza: ['', Validators.required],
      // datumKontraindikacije: ['', Validators.required]
    });
  }

  get f() {
    return this.consentFormGroup.controls;
  }

  updateConsentWithConformation() {
    this.dialog.open(ConformationDialogComponent, {
      data:
      {
        naslov: "Ažuriranje saglasnosti",
        poruka: "Jeste li sigurni da ste uneli tačne podatke u saglasnost za imunizaciju?"
      },
    }).afterClosed().subscribe(result => {
      if (result) {
        this.updateConsent();
      }
    });
  }

  updateConsent() {
    if (this.korisnik) {
      this.Podaci_o_lekaru.Ime = this.korisnik.Korisnik.Ime;
      this.Podaci_o_lekaru.Prezime = this.korisnik.Korisnik.Ime;
      this.Podaci_o_lekaru.JMBG = this.korisnik.Korisnik.KorisnikID;
      this.Podaci_o_lekaru.Faksimil = this.consentFormGroup.get('faksimil')?.value;
      this.Podaci_o_lekaru.Broj_telefona = this.consentFormGroup.get('telefon')?.value;
    }

    this.Doza['@']['Redni_broj'] = this.getDozaRedniBroj();
    this.Doza['util:Datum']['#'] = moment(moment.now()).format('YYYY-MM-DD');
    this.Doza['util:Serija']['#'] = '12312421';
    this.Doza['util:Proizvodjac']['#'] = 'BionTech';
    this.Doza['util:Tip']['#'] = this.getDozaTip(JSON.stringify(this.consentToUpdate!.Saglasnost.Izjava));
    this.Doza['util:Ekstremitet']['#'] = this.ekstremitet;
    this.Doza['util:Nezeljena_rekacija']['#'] = this.consentFormGroup.get('nezeljenaReakcija')?.value;

    this.Kontraindikacija.Datum = moment(moment.now()).format('YYYY-MM-DD');
    this.Kontraindikacija.Dijagnoza = this.consentFormGroup.get('dijagnoza')?.value;
    this.Kontraindikacije.Privremena_kontraindikacija.push(this.Kontraindikacija);
    this.Kontraindikacije.Odluka_komisije_o_trajnim = 'Ne';

    this.consentService.updateConsent(this.getUpdatedConsent())
      .subscribe(response => {
        this.snackBarService.openSnackBar(response.body as string);
        if (response.status == 200)
          this.router.navigate(["imunizacija-app/saglasnost/drugi-deo-saglasnosti"]);
      })
  }

  getDozaRedniBroj(): number {
    if (this.consentToUpdate?.Saglasnost.O_vakcinaciji == null) {
      return 1;
    }
    return this.consentToUpdate?.Saglasnost.O_vakcinaciji.Doza['@']['Redni_broj'] + 1;
  }

  getDozaTip(izjava: string): string {
    return izjava.split('\"').slice(-2, -1)[0];
  }

  getUpdatedConsent(): Saglasnost {
    this.Drzavljanstvo = this.parseDrzavljanstvo(JSON.stringify(this.consentToUpdate?.Saglasnost.Drzavljanstvo));
    let saglasnost: Saglasnost = {
      Saglasnost: {
        "@": {
          "xmlns": "http://www.vakc-sistem.rs/saglasnost-za-imunizaciju",
          "xmlns:xsi": "http://www.w3.org/2001/XMLSchema-instance",
          "xmlns:util": "http://www.vakc-sistem.rs/util",
          "xsi:schemaLocation": "http://www.vakc-sistem.rs/saglasnost-za-imunizaciju saglasnost_za_imunizaciju.xsd",
          Id: "7654321"
        },
        Drzavljanstvo: this.Drzavljanstvo,
        Licni_podaci: this.consentToUpdate!.Saglasnost.Licni_podaci,
        Kontakt: this.consentToUpdate!.Saglasnost.Kontakt,
        Zaposlenje: this.consentToUpdate!.Saglasnost.Zaposlenje,
        Izjava: this.consentToUpdate!.Saglasnost.Izjava,
        Datum: this.consentToUpdate!.Saglasnost.Datum,
        O_vakcinaciji: {
          Zdravstvena_ustanova: this.consentFormGroup.get('zdravstvenaUstanova')?.value,
          Punkt: this.consentFormGroup.get('punkt')?.value,
          Podaci_o_lekaru: this.Podaci_o_lekaru,
          Doza: this.Doza,
          Kontraindikacije: this.Kontraindikacije
        }
      }
    }
    return saglasnost;
  }


  parseDrzavljanstvo(drzavljanstvoParsed: string): Drzavljanstvo {
    let personalId = drzavljanstvoParsed.split('\"').slice(-2, -1)[0];
    if (drzavljanstvoParsed.includes('DOMACE')) {
      return {
        "@": { Tip: 'DOMACE' },
        "util:JMBG": personalId
      }
    }
    else if (drzavljanstvoParsed.includes('STRANO_SA_BORAVKOM')) {
      return {
        "@": { Tip: 'STRANO_SA_BORAVKOM' },
        "util:Evidencioni_broj_stranca": personalId
      }
    }
    else {
      return {
        "@": { Tip: 'STRANO_BEZ_BORAVKA' },
        "util:Br_pasosa": personalId
      }
    }
  }

  checkSubmit() {
    return !this.consentFormGroup.valid;
  }


}
