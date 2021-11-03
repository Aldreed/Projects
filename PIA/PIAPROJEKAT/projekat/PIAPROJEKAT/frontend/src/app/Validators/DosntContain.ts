import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function containsNotValidator(nameRe: RegExp): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const containsNot = nameRe.test(control.value);
      return containsNot ?  {containsNot: {value: control.value}}:null;
    };
  }