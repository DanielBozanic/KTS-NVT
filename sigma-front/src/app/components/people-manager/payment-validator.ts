import { AbstractControl } from '@angular/forms';

export function PaymentValidator(
  control: AbstractControl
): { [key: string]: boolean } | null {
  const paymentBigDecimal = control.get('paymentBigDecimal');
  if (paymentBigDecimal?.value < 0) {
    return { paymentBigDecimalInvalid: true };
  } else {
    return null;
  }
}
