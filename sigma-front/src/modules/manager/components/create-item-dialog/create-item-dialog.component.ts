import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {
  MatSnackBar,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { PositiveNumberValidator } from '../../validators/positive-number-validator';

@Component({
  selector: 'app-create-item-dialog',
  templateUrl: './create-item-dialog.component.html',
  styleUrls: ['./create-item-dialog.component.scss'],
})
export class CreateItemDialogComponent implements OnInit {
  @Output() parentCreateNewItem: EventEmitter<any> = new EventEmitter();
  createNewItemForm!: FormGroup;
  types: Array<string>;
  foodIsTrue: boolean;
  fileReader: FileReader;
  selectedFile: string | null;
  RESPONSE_OK: number;
  RESPONSE_ERROR: number;
  verticalPosition: MatSnackBarVerticalPosition;

  constructor(private snackBar: MatSnackBar) {
    this.types = ['APPETISER', 'SALAD', 'MAIN_COURSE', 'DESERT'];
    this.foodIsTrue = true;
    this.fileReader = new FileReader();
    this.selectedFile = null;
    this.RESPONSE_OK = 0;
    this.RESPONSE_ERROR = -1;
    this.verticalPosition = 'top';
  }

  ngOnInit(): void {
    this.createNewItemForm = new FormGroup({
      name: new FormControl('', Validators.required),
      food: new FormControl(true),
      type: new FormControl('APPETISER'),
      buyingPrice: new FormControl(null, Validators.required),
      description: new FormControl('', Validators.required),
      image: new FormControl(''),
    });
  }

  get buyingPrice(): AbstractControl | null {
    return this.createNewItemForm.get('buyingPrice');
  }

  createNewItem(): void {
    console.log(this.selectedFile);
    this.createNewItemForm.patchValue({ image: this.selectedFile });
    this.parentCreateNewItem.emit(this.createNewItemForm.value);
    this.createNewItemForm.reset();
    this.createNewItemForm.patchValue({ food: true });
    this.createNewItemForm.patchValue({
      type: 'APPETISER',
    });
  }

  hasErrorCreateNewItemForm = (controlName: string, errorName: string) => {
    return this.createNewItemForm.controls[controlName].hasError(errorName);
  };

  foodValueChanged(value: boolean): void {
    this.foodIsTrue = value;
  }

  checkBuyingPrice(): void {
    let validBuyingPrice = PositiveNumberValidator(
      this.createNewItemForm.get('buyingPrice')?.value
    );
    if (validBuyingPrice !== null) {
      this.createNewItemForm
        .get('buyingPrice')
        ?.setErrors([{ positiveNumberInvalid: validBuyingPrice }]);
    }
  }

  changeFile(event: any): void {
    this.fileReader.onload = (event: any) => {
      if (!event.target.result.startsWith('data:image')) {
        this.openSnackBar('Image files only allowed!', this.RESPONSE_ERROR);
      } else {
        this.selectedFile = event.target.result;
      }
    };
    this.fileReader.onerror = () => {
      this.selectedFile = null;
    };
    this.fileReader.readAsDataURL(event.target.files[0]);
  }

  openSnackBar(msg: string, responseCode: number) {
    this.snackBar.open(msg, 'x', {
      duration: responseCode === this.RESPONSE_OK ? 3000 : 20000,
      verticalPosition: this.verticalPosition,
      panelClass: responseCode === this.RESPONSE_OK ? 'back-green' : 'back-red',
    });
  }
}
