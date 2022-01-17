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
      this.router.navigate(['administrator']);
    }
  }

  public onToggleSidenav = () => {
    this.sidenavToggle.emit();
  };

  administratorView() {
    this.showView = 'administrator';
  }
}
