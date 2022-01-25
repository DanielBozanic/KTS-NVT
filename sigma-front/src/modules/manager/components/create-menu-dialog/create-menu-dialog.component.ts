import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { DateValidator } from '../../validators/date-validator';

@Component({
  selector: 'app-create-menu-dialog',
  templateUrl: './create-menu-dialog.component.html',
  styleUrls: ['./create-menu-dialog.component.scss'],
})
export class CreateMenuDialogComponent implements OnInit {
  @Output() parentCreateNewMenu: EventEmitter<any> = new EventEmitter();
  createNewMenuForm!: FormGroup;
  constructor() {}

  ngOnInit(): void {
    this.createNewMenuForm = new FormGroup({
      name: new FormControl('', Validators.required),
      startDate: new FormControl('', Validators.required),
      expirationDate: new FormControl(''),
    });
  }

  get startDate(): AbstractControl | null {
    return this.createNewMenuForm.get('startDate');
  }

  get expirationDate(): AbstractControl | null {
    return this.createNewMenuForm.get('expirationDate');
  }

  createNewMenu(): void {
    this.parentCreateNewMenu.emit(this.createNewMenuForm.value);
    this.createNewMenuForm.reset();
  }

  hasErrorCreateNewMenuForm = (controlName: string, errorName: string) => {
    return this.createNewMenuForm.controls[controlName].hasError(errorName);
  };

  checkDate(): void {
    let validStartDate = DateValidator(
      this.createNewMenuForm.get('startDate')?.value
    );
    if (validStartDate !== null) {
      this.createNewMenuForm
        .get('startDate')
        ?.setErrors([{ dateInvalid: validStartDate }]);
    }
  }
}
