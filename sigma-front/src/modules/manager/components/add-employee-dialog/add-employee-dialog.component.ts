import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { PositiveNumberValidator } from '../../validators/positive-number-validator';

@Component({
  selector: 'app-add-employee-dialog',
  templateUrl: './add-employee-dialog.component.html',
  styleUrls: ['./add-employee-dialog.component.scss'],
})
export class AddEmployeeDialogComponent implements OnInit {
  @Output() parentAddEmployee: EventEmitter<any> = new EventEmitter();
  addEmployeeForm!: FormGroup;
  jobs: string[];

  constructor() {
    this.jobs = ['BARTENDER', 'COOK', 'WAITER'];
  }

  ngOnInit(): void {
    this.addEmployeeForm = new FormGroup({
      name: new FormControl('', Validators.required),
      paymentBigDecimal: new FormControl(null, Validators.required),
      type: new FormControl('BARTENDER'),
    });
  }

  get paymentBigDecimalAddForm(): AbstractControl | null {
    return this.addEmployeeForm.get('paymentBigDecimal');
  }

  hasError = (controlName: string, errorName: string) => {
    return this.addEmployeeForm.controls[controlName].hasError(errorName);
  };

  checkPayment(): void {
    let error = PositiveNumberValidator(
      this.addEmployeeForm.get('paymentBigDecimal')?.value
    );
    if (error !== null) {
      this.addEmployeeForm
        .get('paymentBigDecimal')
        ?.setErrors([{ positiveNumberInvalid: error }]);
    }
  }

  addEmployee(): void {
    this.parentAddEmployee.emit(this.addEmployeeForm?.value);
    this.addEmployeeForm.reset();
    this.addEmployeeForm.patchValue({
      type: 'BARTENDER',
    });
  }
}
