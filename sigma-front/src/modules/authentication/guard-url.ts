import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Router, ActivatedRouteSnapshot, CanActivate } from '@angular/router';
import { TokenStorageService } from './token-storage.service';

const TOKEN_KEY = 'AuthToken';

@Injectable()
export class RoleGuardService implements CanActivate {
  constructor(
    public auth: AuthService,
    public router: Router,
    private tokenStorage: TokenStorageService
  ) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const token = this.tokenStorage.getToken();

    if (!token) {
      window.alert('Ulogujte se!');
      return false;
    }

    return true;
  }
}
