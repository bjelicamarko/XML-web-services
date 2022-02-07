import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function jmbgValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (!value) {
      return null;
    }

    if (/(0[1-9]|[12]\d|3[01])(0[1-9]|1[0-2])\d{3}\d{2}\d{4}/.test(value)) {
      return null;
    }

    return { jmbg: { invalid: true } };
  };
}