import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { TokenStorageService } from '../token-storage.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';
import {
  MatSnackBar,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  role: string = 'guest';
  RESPONSE_OK: number;
  RESPONSE_ERROR: number;
  verticalPosition: MatSnackBarVerticalPosition;

  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.verticalPosition = 'top';
    this.RESPONSE_OK = 0;
    this.RESPONSE_ERROR = -1;
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.role = 'admin';
    }
  }

  onSubmit(): void {
    const { username, password } = this.form.value;
    this.authService.login(username, password).subscribe(
      (data) => {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.role = 'admin';
        this.openSnackBar('Succesfully logged in', this.RESPONSE_OK);
        this.router.navigate(['profile']);
      },
      (err) => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
        this.openSnackBar(
          'Username and/or password wrong',
          this.RESPONSE_ERROR
        );
      }
    );
  }

  signOut(): void {
    this.tokenStorage.signOut();
    this.isLoggedIn = false;
    this.role = 'guest';
    this.router.navigate(['/waiterTables']);
    window.location.reload();
  }

  reloadPage(): void {
    window.location.reload();
  }

  openSnackBar(msg: string, responseCode: number) {
    this.snackBar.open(msg, 'x', {
      duration: responseCode === this.RESPONSE_OK ? 3000 : 20000,
      verticalPosition: this.verticalPosition,
      panelClass: responseCode === this.RESPONSE_OK ? 'back-green' : 'back-red',
    });
  }
}
