import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { PositiveNumberValidator } from '../../validators/positive-number-validator';

@Component({
  selector: 'app-table-dialog',
  templateUrl: './table-dialog.component.html',
  styleUrls: ['./table-dialog.component.scss'],
})
export class TableDialogComponent implements OnInit {
  @Input() addOrEditButton: string = '';
  @Output() addOrEditFunction: EventEmitter<any> = new EventEmitter();
  @Output() deleteTableFunction: EventEmitter<any> = new EventEmitter();

  tableForm!: FormGroup;

  constructor() {}

  ngOnInit(): void {
    this.tableForm = new FormGroup({
      numberOfChairs: new FormControl(null, Validators.required),
    });
  }

  get numberOfChairs(): AbstractControl | null {
    return this.tableForm.get('numberOfChairs');
  }

  addOrEditFunctionDialog(): void {
    this.addOrEditFunction.emit(this.numberOfChairs?.value);
  }

  removeTable(): void {
    this.deleteTableFunction.emit();
  }

  hasErrorTableForm = (controlName: string, errorName: string) => {
    return this.tableForm.controls[controlName].hasError(errorName);
  };

  checkNumberOfChairs(): void {
    let validNumberOfChairs = PositiveNumberValidator(
      this.tableForm.get('numberOfChairs')?.value
    );
    if (validNumberOfChairs !== null) {
      this.tableForm
        .get('numberOfChairs')
        ?.setErrors([{ positiveNumberInvalid: validNumberOfChairs }]);
    }
  }
}
