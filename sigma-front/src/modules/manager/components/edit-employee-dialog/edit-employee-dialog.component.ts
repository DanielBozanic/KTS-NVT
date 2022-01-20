import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { PositiveNumberValidator } from '../../validators/positive-number-validator';

@Component({
  selector: 'app-edit-employee-dialog',
  templateUrl: './edit-employee-dialog.component.html',
  styleUrls: ['./edit-employee-dialog.component.scss'],
})
export class EditEmployeeDialogComponent implements OnInit {
  @Output() parentEditEmployee: EventEmitter<any> = new EventEmitter();
  editEmployeeForm!: FormGroup;

  constructor() {}

  ngOnInit(): void {
    this.editEmployeeForm = new FormGroup({
      id: new FormControl(),
      name: new FormControl(),
      paymentBigDecimal: new FormControl(),
    });
  }

  get paymentBigDecimalEditForm(): AbstractControl | null {
    return this.editEmployeeForm.get('paymentBigDecimal');
  }

  hasError = (controlName: string, errorName: string) => {
    return this.editEmployeeForm.controls[controlName].hasError(errorName);
  };

  checkPayment(): void {
    let error = PositiveNumberValidator(
      this.editEmployeeForm.get('paymentBigDecimal')?.value
    );
    if (error !== null) {
      this.editEmployeeForm
        .get('paymentBigDecimal')
        ?.setErrors([{ positiveNumberInvalid: error }]);
    }
  }

  editEmployee(): void {
    this.parentEditEmployee.emit(this.editEmployeeForm?.value);
    this.editEmployeeForm.reset();
  }
}
