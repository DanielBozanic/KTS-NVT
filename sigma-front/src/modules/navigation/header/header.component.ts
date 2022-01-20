import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../../authentication/token-storage.service';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  @Output() public sidenavToggle = new EventEmitter();
  showView: string = 'notregister';
  constructor(
    private router: Router,
    private tokenStorage: TokenStorageService
  ) {}

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.administratorView();
      this.router.navigate(['profile']);
    }
  }

  public onToggleSidenav = () => {
    this.sidenavToggle.emit();
  };

  signOut(): void {
    this.tokenStorage.signOut();
    this.showView = 'notregister';
    this.reloadPage(); //change later
  }

  reloadPage(): void {
    window.location.reload();
  }

  administratorView() {
    this.showView = 'administrator';
  }
}