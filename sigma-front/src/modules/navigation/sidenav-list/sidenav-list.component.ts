import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../../authentication/token-storage.service';

@Component({
  selector: 'app-sidenav-list',
  templateUrl: './sidenav-list.component.html',
  styleUrls: ['./sidenav-list.component.scss'],
})
export class SidenavListComponent implements OnInit {
  @Output() sidenavClose = new EventEmitter();
  showView: string = 'notregister';
  constructor(
    private router: Router,
    private tokenStorage: TokenStorageService
  ) {}

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.router.navigate(['profile']);
    }
    return true;
  }

  public onSidenavClose = () => {
    this.sidenavClose.emit();
  };

  administratorView() {
    this.showView = 'administrator';
  }
}
