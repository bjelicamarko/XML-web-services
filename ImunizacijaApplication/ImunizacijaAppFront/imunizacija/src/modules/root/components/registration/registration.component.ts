import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { emailValidator } from 'src/modules/shared/directives/custom-validators/email-validator';
import { jmbgValidator } from 'src/modules/shared/directives/custom-validators/jmbg-validator';
import { pasosValidator } from 'src/modules/shared/directives/custom-validators/pasos-validator';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { Role } from '../../models/enums/role';
import { RegistrationData } from '../../models/registrationData';
import { UserRegistrationDTO } from '../../models/userRegistrationDTO';
import { RegistrationService } from '../../services/registration.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent {

  options: string[] = ['Drzavljanin Republike Srbije',
    'Strani drzavljanin sa boravkom u RS',
    'Strani drzavljanin bez boravka u RS'];
  selectedValue: string = 'Drzavljanin Republike Srbije';


  registrationFormGroup: FormGroup;

  constructor(private fb: FormBuilder, private registrationService: RegistrationService, private snackBarService: SnackBarService, private router: Router) {
    this.registrationFormGroup = this.fb.group({
      userId: ['', [Validators.required]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, emailValidator()]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(10)]],
    });
  }

  get f() {
    return this.registrationFormGroup.controls;
  }

  onChange(_any: any) {
    this.selectedValue = _any;
    if (this.selectedValue === 'Drzavljanin Republike Srbije') {
      this.registrationFormGroup.get('userId')?.setValidators(Validators.compose([Validators.required, jmbgValidator()]));
    } else if (this.selectedValue === 'Strani drzavljanin sa boravkom u RS') {
      this.registrationFormGroup.get('userId')?.setValidators(Validators.compose([Validators.required]));
    } else if (this.selectedValue === 'Strani drzavljanin bez boravka u RS') {
      this.registrationFormGroup.get('userId')?.setValidators(Validators.compose([Validators.required, pasosValidator()]));
    }
    this.registrationFormGroup.get('userId')?.setValue('');
    this.checkSubmit();
  }

  public register() {
    if (!this.registrationFormGroup.valid) {
      this.snackBarService.openSnackBar("Nevalidni podaci za unos!");
      return;
    }
    let userid = "";
    let nationalityType = "";
    if (this.selectedValue === 'Drzavljanin Republike Srbije') {
      userid = this.registrationFormGroup.get('userId')?.value;
      nationalityType = "RS";
    } else if (this.selectedValue === 'Strani drzavljanin sa boravkom u RS') {
      userid = this.registrationFormGroup.get('userId')?.value;
      nationalityType = "FOREIGN_W_STAY";
    } else if (this.selectedValue === 'Strani drzavljanin bez boravka u RS') {
      userid = this.registrationFormGroup.get('userId')?.value;
      nationalityType = "FOREIGN_W_PASSPORT";
    }
    let fn = this.registrationFormGroup.get('firstName')?.value;
    let ln = this.registrationFormGroup.get('lastName')?.value;
    let email = this.registrationFormGroup.get('email')?.value;
    let password = this.registrationFormGroup.get('password')?.value;
    let userDTO: UserRegistrationDTO = { registrationDTO: { email: email, firstName: fn, lastName: ln, password: password, userId: userid, nationalityType: nationalityType } };
    this.registrationService.registerUser(userDTO).subscribe((res) => {
      if (res.body != null) {
        this.snackBarService.openSnackBar("Uspešno ste se registrovali!");
        this.router.navigate(["imunizacija-app/auth/login"]);
      }
    }, (err) => {
      if (err.error)
        this.snackBarService.openSnackBar(err.error);
    });
  }

  checkSubmit() {
    return !this.registrationFormGroup.valid;
  }

}
