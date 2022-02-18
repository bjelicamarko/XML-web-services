import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function pasosValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (!value) {
      return null;
    }

    if (/[0-9A-Z]{6,12}/.test(value)) {
      return null;
    }

    return { pasos: { invalid: true } };
  };
}