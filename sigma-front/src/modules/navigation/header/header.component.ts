import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { Globals } from 'src/modules/root/globals';
import { TokenStorageService } from '../../authentication/token-storage.service';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  @Output() public sidenavToggle = new EventEmitter();
  showView: string = 'notregister';
  globals: Globals;

  constructor(
    private router: Router,
    private tokenStorage: TokenStorageService,
    globals: Globals,
  ) {
    this.globals = globals;
  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.administratorView();
    }
  }

  public onToggleSidenav = () => {
    this.sidenavToggle.emit();
  };

  signOut(): void {
    this.tokenStorage.signOut();
    this.showView = 'notregister';
    this.router.navigate(['login']);
    this.reloadPage(); //change later
  }

  reloadPage(): void {
    window.location.reload();
  }

  administratorView() {
    this.showView = 'administrator';
  }
}
