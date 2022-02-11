import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function pasosValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (!value) {
      return null;
    }

    if (/^[A-Z0-9<]{9}[0-9]{1}[A-Z]{3}[0-9]{7}[A-Z]{1}[0-9]{7}[A-Z0-9<]{14}[0-9]{2}$/.test(value)) {
      return null;
    }

    // if (/^abc/.test(value)) {
    //   return null;
    // }

    return { pasos: { invalid: true } };
  };
}