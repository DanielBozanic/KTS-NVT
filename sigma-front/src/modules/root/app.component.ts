import { Component } from '@angular/core';
import { TokenStorageService } from '../authentication/token-storage.service';
import { LoginComponent } from '../authentication/login/login.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'sigma-front';
  private role: string;

  constructor(private tokenStorage: TokenStorageService) {}

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.role = 'admin';
    }
  }
}
