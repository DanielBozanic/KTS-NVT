import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";

@Component({
    selector: 'app-code-verification-dialog',
    templateUrl: './code-verification-dialog.component.html',
    styleUrls: ['./code-verification-dialog.component.scss'],
})
export class CodeVerificationDialogComponent implements OnInit {
    @Output() parentCheckCode: EventEmitter<any> = new EventEmitter();
    codeForm!: FormGroup;

    constructor() {
    }

    get codeFromDialog() {
        return this.codeForm.get('code') as FormControl;
    }

    ngOnInit(): void {
        this.codeForm = new FormGroup({
            code: new FormControl('', Validators.required)
        });
    }

    checkCode(): void {
        this.parentCheckCode.emit(this.codeForm.get('code')?.value);
        this.codeForm.reset();
    }
}