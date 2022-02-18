import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';
import { jmbgValidator } from 'src/modules/shared/directives/custom-validators/jmbg-validator';
import { pasosValidator } from 'src/modules/shared/directives/custom-validators/pasos-validator';
import { Podnosilac } from 'src/modules/shared/models/LicniPodaci';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { Zahtev } from '../../models/Zahtev';
import { RequestService } from '../../services/request.service';

import { ToolbarService, LinkService, ImageService, HtmlEditorService, TableService } from '@syncfusion/ej2-angular-richtexteditor';
import { Drzavljanstvo } from 'src/modules/shared/models/Drzavljanstvo';
import { UtilService } from 'src/modules/shared/services/util.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-request-page',
  templateUrl: './request-page.component.html',
  styleUrls: ['./request-page.component.scss'],
  providers: [ToolbarService, LinkService, ImageService, HtmlEditorService, TableService]
})
export class RequestPageComponent implements OnInit {
  
  public tools: object = {
    items: ['Undo', 'Redo', '|',
        'Bold', 'Italic']
  };
  public iframe: object = { enable: true };
  public height: number = 200;
  public value: string = "";

  registrationFormGroup: FormGroup;

  genderOptions: string[];
  options: string[];
  selectedValue: string;
  userIdType: string = 'DOMACE';

  Drzavljanstvo: Drzavljanstvo = {
    "@": {
      Tip: this.userIdType
    }
  };

  Podnosilac: Podnosilac = {
    Ime: {
      '@': { xmlns: 'http://www.vakc-sistem.rs/util' },
      '#': ''
    },
    Prezime: {
      '@': { xmlns: 'http://www.vakc-sistem.rs/util' },
      '#': ''
    },
    Datum_rodjenja: {
      '@': { xmlns: 'http://www.vakc-sistem.rs/util' },
      '#': ''
    },
    Pol: {
      '@': { xmlns: 'http://www.vakc-sistem.rs/util' },
      '#': ''
    },
    Drzavljanstvo: this.Drzavljanstvo
  };

  constructor(private fb: FormBuilder, private requestService: RequestService,
              private snackBarService: SnackBarService, private utilService: UtilService, 
              private router: Router) { 
    this.genderOptions = ['Muski', 'Zenski'];
    this.options = ['Drzavljanin Republike Srbije',
                    'Strani drzavljanin sa boravkom u RS',
                    'Strani drzavljanin bez boravka u RS'];
    this.selectedValue = 'Drzavljanin Republike Srbije';

    this.registrationFormGroup = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthDate: ['', Validators.required],
      gender: ['Musko', Validators.required],
      userId: ['', [Validators.required, jmbgValidator()]],
      reason: [''],
      place: ['Novi Sad', Validators.required],
      date: ['Da', Validators.required]
    });
  }

  ngOnInit(): void {
    this.canCreateRequest(); // Proveri da li moze da kreira zahtev
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

  createRequest() {
    if (this.selectedValue === 'Drzavljanin Republike Srbije') {
      this.Drzavljanstvo['util:JMBG'] = this.registrationFormGroup.get('userId')?.value;
    } else if (this.selectedValue === 'Strani drzavljanin sa boravkom u RS') {
      this.Drzavljanstvo['util:Evidencioni_broj_stranca'] = this.registrationFormGroup.get('userId')?.value;
    } else if (this.selectedValue === 'Strani drzavljanin bez boravka u RS') {
      this.Drzavljanstvo['util:Br_pasosa'] = this.registrationFormGroup.get('userId')?.value;
    }
    this.Drzavljanstvo['@'].Tip = this.userIdType;

    this.Podnosilac.Ime['#'] = this.registrationFormGroup.get('firstName')?.value;
    this.Podnosilac.Prezime['#'] = this.registrationFormGroup.get('lastName')?.value;
    this.Podnosilac.Datum_rodjenja['#'] = moment(this.registrationFormGroup.get('birthDate')?.value).format('YYYY-MM-DD');
    this.Podnosilac.Pol['#'] = this.registrationFormGroup.get('gender')?.value;

    let newRequest: Zahtev = this.createNewRequest();
    // console.log(newRequest);

    this.requestService.createRequest(newRequest).subscribe((response) => {
      this.snackBarService.openSnackBar(response.body as string);
          this.resetStateOfForm();
    }, (error) => {
      this.snackBarService.openSnackBar(error.error);
      this.router.navigate(["imunizacija-app/interesovanje/novo-interesovanje"]);
    });
  }

  createRequestWithCheckValue() : void {
    if (this.value) {
      this.createRequest();
    } else {
      this.snackBarService.openSnackBar("Popunite razlog.");
    }
  }

  createNewRequest() : Zahtev {
      this.convertValue();  
      let zahtev: Zahtev = {
        Zahtev: {
          "@": {
            "xmlns" : "http://www.vakc-sistem.rs/zahtev-dzs",
            "xmlns:util" : "http://www.vakc-sistem.rs/util",
            "xmlns:xsi": "http://www.w3.org/2001/XMLSchema-instance",
            "xsi:schemaLocation": "http://www.vakc-sistem.rs/zahtev-dzs zahtev_za_izdavanje_zelenog.xsd",
            Id: "1"
          },
          Podnosilac: this.Podnosilac,
          Razlog: this.value,
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
      reason: [''],
      place: ['Novi Sad', Validators.required],
      date: ['Da', Validators.required]
    });
    this.value = '';
  }

  convertValue() {
   ///<*> ?*? <*/> ?*/?
    // console.log(this.value);
    let r = this.value.replace(/<\/p>/g, '');
    r = r.replace(/<p>/g, '');
    r = r.replace(/<\/strong>/g,"|+,");
    r = r.replace(/<strong>/g,",+");
    r = r.replace(/<\/em>/g,"|~,");
    r = r.replace(/<em>/g,",~");
    r = r.replace(',,', ',');
    // console.log(r);
    this.value = r;
  }

  canCreateRequest(): boolean {
    this.requestService.canCreateRequest(this.utilService.getLoggedUserID()).subscribe((response) => {
      if(response.body) { }
    }, (error) => {
      this.snackBarService.openSnackBar(error.error);
      this.router.navigate(["imunizacija-app/interesovanje/novo-interesovanje"]);
    });
    
    return true;
  }
}
