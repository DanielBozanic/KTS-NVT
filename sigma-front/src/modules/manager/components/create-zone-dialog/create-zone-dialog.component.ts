import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-zone-dialog',
  templateUrl: './create-zone-dialog.component.html',
  styleUrls: ['./create-zone-dialog.component.scss'],
})
export class CreateZoneDialogComponent implements OnInit {
  @Output() parentCreateNewZone: EventEmitter<any> = new EventEmitter();
  createNewZoneForm!: FormGroup;

  constructor() {}

  ngOnInit(): void {
    this.createNewZoneForm = new FormGroup({
      name: new FormControl(null, Validators.required),
    });
  }

  hasErrorCreateNewZoneForm = (controlName: string, errorName: string) => {
    return this.createNewZoneForm.controls[controlName].hasError(errorName);
  };

  createNewZone(): void {
    this.parentCreateNewZone.emit(this.createNewZoneForm?.value);
    this.createNewZoneForm.reset();
  }
}
