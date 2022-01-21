import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { PositiveNumberValidator } from '../../validators/positive-number-validator';

@Component({
  selector: 'app-add-item-in-menu-dialog',
  templateUrl: './add-item-in-menu-dialog.component.html',
  styleUrls: ['./add-item-in-menu-dialog.component.scss'],
})
export class AddItemInMenuDialogComponent implements OnInit {
  @Output() parentAddItemInMenu: EventEmitter<any> = new EventEmitter();
  addItemInMenuForm!: FormGroup;
  constructor() {}

  ngOnInit(): void {
    this.addItemInMenuForm = new FormGroup({
      id: new FormControl(),
      sellingPrice: new FormControl(null, Validators.required),
    });
  }

  get sellingPrice(): AbstractControl | null {
    return this.addItemInMenuForm.get('sellingPrice');
  }

  addItemInMenu(): void {
    this.parentAddItemInMenu.emit(this.addItemInMenuForm.value);
    this.addItemInMenuForm.reset();
  }

  hasErrorAddItemInMenuForm = (controlName: string, errorName: string) => {
    return this.addItemInMenuForm.controls[controlName].hasError(errorName);
  };

  checkSellingPrice(): void {
    let validSellingPrice = PositiveNumberValidator(
      this.addItemInMenuForm.get('sellingPrice')?.value
    );
    if (validSellingPrice !== null) {
      this.addItemInMenuForm
        .get('sellingPrice')
        ?.setErrors([{ positiveNumberInvalid: validSellingPrice }]);
    }
  }
}
