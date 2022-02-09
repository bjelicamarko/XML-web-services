import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { emailValidator } from 'src/modules/shared/directives/custom-validators/email-validator';
import { jmbgValidator } from 'src/modules/shared/directives/custom-validators/jmbg-validator';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';
import { Role } from '../../models/enums/role';
import { RegistrationData } from '../../models/registrationData';
import { RegistrationService } from '../../services/registration.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent {

  registrationData: RegistrationData;
  registrationFormGroup: FormGroup;

  constructor(private fb: FormBuilder, private registrationService: RegistrationService, private snackBarService: SnackBarService) {
    this.registrationFormGroup = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, emailValidator()]],
      jmbg: ['', [Validators.required, jmbgValidator()]],
    });
    this.registrationData = this.initializeRegistrationData();
  }

  initializeRegistrationData(): RegistrationData {
    return {
      firstName: "",
      lastName: "",
      email: "",
      jmbg: "",
      role: Role.GRADJANIN
    }
  }

  get f() {
    return this.registrationFormGroup.controls;
  }

  public register() {
    this.snackBarService.openSnackBar("Uspešno ste se registrovali!");
    // this.registrationService.registerUser(this.registrationData).subscribe((res) => {
    //   if (res.body != null) {
    //     this.snackBarService.openSnackBar("Uspešno ste se registrovali!");
    //   }
    // }, (err) => {
    //   if (err.error)
    //     this.snackBarService.openSnackBar(String(err.console));
    // });
  }

  checkSubmit() {
    return !this.registrationFormGroup.valid;
  }

}
