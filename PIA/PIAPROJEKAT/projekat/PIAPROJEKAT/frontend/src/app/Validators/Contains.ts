import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function containsValidator(nameRe: RegExp): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const contains = nameRe.test(control.value);
      return contains ?  null:{contains: {value: control.value}};
    };
  }