import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { emailValidator } from 'src/modules/shared/directives/custom-validators/email-validator';
import { jmbgValidator } from 'src/modules/shared/directives/custom-validators/jmbg-validator';
import { pasosValidator } from 'src/modules/shared/directives/custom-validators/pasos-validator';
import { SnackBarService } from 'src/modules/shared/services/snack-bar.service';

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

  townOptions: string[] = ['Novi Sad', 'Novi Bečej', 'Beograd', 'Subotica'];
  selectedOptionForVaccine: string = 'all';

  checks: Array<string> = ['Pfizer-BioNTech', 'Sputnik V (Gamaleya истраживачки центар)', 
  'Sinopharm', 'AstraZeneca', 'Moderna'];
  
  checkedValues: Array<string> = ['Pfizer-BioNTech', 'Sputnik V (Gamaleya истраживачки центар)', 
  'Sinopharm', 'AstraZeneca', 'Moderna'];

  constructor(private fb: FormBuilder,  private snackBarService: SnackBarService) { 
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
  }

  get f() {
    return this.registrationFormGroup.controls;
  }

  public createInterest() {
    console.log(this.registrationFormGroup.value);
    this.snackBarService.openSnackBar("Uspešno ste se pondeli interesovanje!");
  }

  checkSubmit() {
    return !this.registrationFormGroup.valid;
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
  
  handleChange(_any: any){
    this.selectedOptionForVaccine = _any.target.value;
    if (this.selectedOptionForVaccine === 'all') {
      this.resetCheckedValues();
    } else {  // 'specific' case
      this.checkedValues = [];
    }
    console.log(this.checkedValues);
  }

  onCheckChange(_any: any) {
    if(_any.target.checked){
      this.checkedValues.push(_any.target.value);
    } else {
      this.removeCheckedValue(_any.target.value);
    }
    console.log(this.checkedValues);
  }

  resetCheckedValues() {
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
}
