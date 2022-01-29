import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Router, ActivatedRouteSnapshot, CanActivate } from '@angular/router';
import { TokenStorageService } from './token-storage.service';
import { MatSnackBar } from '@angular/material/snack-bar';

const TOKEN_KEY = 'AuthToken';

@Injectable()
export class RoleGuardService implements CanActivate {
  constructor(
    public auth: AuthService,
    public router: Router,
    private tokenStorage: TokenStorageService,
    private snackBar: MatSnackBar,
  ) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const token = this.tokenStorage.getToken();
    if (!token) {
      this.snackBar.open('You are not logged in!', 'x', {
        duration: 5000,
        verticalPosition: 'top',
        panelClass: 'back-red'
      })
      return false;
    }

    return true;
  }
}
