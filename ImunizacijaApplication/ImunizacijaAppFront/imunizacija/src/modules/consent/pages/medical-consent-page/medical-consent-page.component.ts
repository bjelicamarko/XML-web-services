import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { ConformationDialogComponent } from 'src/modules/shared/components/conformation-dialog/conformation-dialog.component';
import { Vakcine } from 'src/modules/shared/enums/Vakcine';
import { Doza_detaljnije } from 'src/modules/shared/models/Doza';
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
  consentId: string = '';
  consentExist: boolean = false;
  consentToUpdate: Saglasnost | undefined;
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
  vaccineInfo: any = {
    Serija: '',
    Proizvodjac: '',
    Tip: ''
  };

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
    this.ekstremitet = this.ekstremiteti[0];
    this.consentFormGroup = this.fb.group({
      zdravstvenaUstanova: ['', [Validators.required]],
      punkt: ['', Validators.required],
      faksimil: ['', Validators.required],
      telefon: ['', Validators.required],
      nezeljenaReakcija: [''],
      dijagnoza: [''],
      odlukaKomisije: ['Ne']
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
      this.Podaci_o_lekaru.Prezime = this.korisnik.Korisnik.Prezime;
      this.Podaci_o_lekaru.JMBG = this.korisnik.Korisnik.KorisnikID;
      this.Podaci_o_lekaru.Faksimil = this.consentFormGroup.get('faksimil')?.value;
      this.Podaci_o_lekaru.Broj_telefona = this.consentFormGroup.get('telefon')?.value;
    }

    this.Doza['@']['Redni_broj'] = 1; // this.getDozaRedniBroj()
    this.Doza['util:Datum']['#'] = moment(moment.now()).format('YYYY-MM-DD');
    this.Doza['util:Serija']['#'] = this.vaccineInfo.Serija;
    this.Doza['util:Proizvodjac']['#'] = this.vaccineInfo.Proizvodjac;
    this.Doza['util:Tip']['#'] = this.vaccineInfo.Tip;
    this.Doza['util:Ekstremitet']['#'] = this.ekstremitet;
    if (this.consentFormGroup.get('nezeljenaReakcija')?.value === '')
      this.Doza['util:Nezeljena_rekacija']['#'] = "Nema nezeljene reakcije.";
    else
      this.Doza['util:Nezeljena_rekacija']['#'] = this.consentFormGroup.get('nezeljenaReakcija')?.value;

    this.Kontraindikacija.Datum = moment(moment.now()).format('YYYY-MM-DD');
    if (this.consentFormGroup.get('dijagnoza')?.value === '')
      this.Kontraindikacija.Dijagnoza = "Nema kontraindikacije.";
    else
      this.Kontraindikacija.Dijagnoza = this.consentFormGroup.get('dijagnoza')?.value;

    this.Kontraindikacije.Privremena_kontraindikacija.push(this.Kontraindikacija);
    this.Kontraindikacije.Odluka_komisije_o_trajnim = this.consentFormGroup.get('odlukaKomisije')?.value;

    this.consentService.updateConsent(this.getUpdatedConsent())
      .subscribe(response => {
        this.snackBarService.openSnackBar(response.body as string);
        if (response.status == 200)
          window.location.reload();
      })
  }
  getVaccineInfo(vaccineType: string): any {
    if (Vakcine.PFIZER.split("|")[0] === vaccineType) {
      let vaccineInfoSplitted = Vakcine.PFIZER.split("|");
      return {
        Tip: vaccineInfoSplitted[0],
        Proizvodjac: vaccineInfoSplitted[1],
        Serija: vaccineInfoSplitted[2]
      }
    }
    else if (Vakcine.SPUTNIKV.split("|")[0] === vaccineType) {
      let vaccineInfoSplitted = Vakcine.SPUTNIKV.split("|");
      return {
        Tip: vaccineInfoSplitted[0],
        Proizvodjac: vaccineInfoSplitted[1],
        Serija: vaccineInfoSplitted[2]
      }
    }
    else if (Vakcine.SINOPHARM.split("|")[0] === vaccineType) {
      let vaccineInfoSplitted = Vakcine.SINOPHARM.split("|");
      return {
        Tip: vaccineInfoSplitted[0],
        Proizvodjac: vaccineInfoSplitted[1],
        Serija: vaccineInfoSplitted[2]
      }
    }
    else if (Vakcine.ASTRAZENECA.split("|")[0] === vaccineType) {
      let vaccineInfoSplitted = Vakcine.ASTRAZENECA.split("|");
      return {
        Tip: vaccineInfoSplitted[0],
        Proizvodjac: vaccineInfoSplitted[1],
        Serija: vaccineInfoSplitted[2]
      }
    }
    else {
      let vaccineInfoSplitted = Vakcine.MODERNA.split("|");
      return {
        Tip: vaccineInfoSplitted[0],
        Proizvodjac: vaccineInfoSplitted[1],
        Serija: vaccineInfoSplitted[2]
      }
    }
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
          Id: this.consentId
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

  isConsentExist() {
    this.consentService.isConsentExist(this.consentId)
      .subscribe(response => {
        this.snackBarService.openSnackBar(response.body as string);
        this.consentExist = true;
        this.consentService.getConsentById(this.consentId + ".xml")
          .subscribe(response => {
            if (response.body)
              this.consentToUpdate = this.utilService.parseXml(response.body);
            this.vaccineInfo = this.getVaccineInfo(this.getDozaTip(JSON.stringify(this.consentToUpdate!.Saglasnost.Izjava)));
          })
        let userId = this.utilService.getLoggedUserID();
        this.utilService.getUser(userId + ".xml")
          .subscribe(response => {
            if (response.body)
              this.korisnik = this.utilService.parseXml(response.body);
          })
      },
        error => {
          this.snackBarService.openSnackBar(error.error as string);
          this.consentExist = false;
        })
  }

}
