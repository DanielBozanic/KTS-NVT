import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../token-storage.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  currentUser: any;

  constructor(private token: TokenStorageService) {}

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.currentUser = this.token.getUser();
    }
  }
}
