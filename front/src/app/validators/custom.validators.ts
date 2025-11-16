import {AbstractControl, ValidationErrors} from '@angular/forms';
import Big from 'big.js';

export class CustomValidators {
  static bigDecimalValid(control: AbstractControl): ValidationErrors | null {
    const value = control.value as string;
    if (!value) {
      return null;
    }
    try {
      Big(value);
      return null;
    } catch (e) {
      return {bigDecimalNotValid: true};
    }
  }
}
