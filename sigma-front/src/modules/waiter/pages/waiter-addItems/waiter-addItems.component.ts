import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {
  MatSnackBar,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Item } from 'src/modules/root/models/item';
import { NotificationDTO } from 'src/modules/root/models/notification';
import { Table } from 'src/modules/root/models/table';
import { WebSocketAPI } from 'src/modules/root/WebSocketApi';

@Component({
  selector: 'app-waiter-addItems',
  templateUrl: './waiter-addItems.component.html',
  styleUrls: ['./waiter-addItems.component.scss'],
})
export class WaiterAddItemsComponent implements OnInit {
  RESPONSE_OK: number;
  RESPONSE_ERROR: number;
  verticalPosition: MatSnackBarVerticalPosition;
  table: Table;
  code!: number;
  sentRequestEarlier: boolean = false;

  constructor(
    private snackBar: MatSnackBar,
    private codeVerificationDialog: MatDialog,
    private router: Router,
    private webSocketOrderCreation: WebSocketAPI,
  ) {
    this.table = new Table();
    this.RESPONSE_OK = 0;
    this.RESPONSE_ERROR = -1;
    this.verticalPosition = 'top';
  }

  @ViewChild('codeVerificationDialog') codeDialog!: TemplateRef<any>;

  ngOnInit(): void {
    this.table = history.state.data;
    this.webSocketOrderCreation = new WebSocketAPI()
    this.webSocketOrderCreation._connect('order', this.handleOrderCreation);
  }

  ngOnDestroy(): void {
    this.webSocketOrderCreation._disconnect();
  }

  checkCode(code: number): void {
    this.code = code;
  }

  async addItems(items: Array<Item>) {
    if (items.length === 0) {
      this.openSnackBar('You could have just clicked cancel...', this.RESPONSE_ERROR);
      return;
    }

    const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
    await dialogRef.afterClosed().toPromise();

    if (this.code) {
      items.forEach(async item => {
        if (this.table.orderId) {
          this.sentRequestEarlier = true;
          await this.webSocketOrderCreation._send(`add-to-order/${this.table.orderId}/${this.code}`, item);
          this.code = 0;
          console.log(item)
        }
      })
    }
  }

  handleOrderCreation = (notification: NotificationDTO) => {
    if (notification.success) {
      if (this.sentRequestEarlier) {
        this.openSnackBar('Successfully added items to order', this.RESPONSE_OK);
        this.router.navigate(['/waiterTables']);
      }
    } else {
      if (this.sentRequestEarlier) {
        this.openSnackBar(notification.message, this.RESPONSE_ERROR);
      }
    }
    this.sentRequestEarlier = false;
  }

  openSnackBar(msg: string, responseCode: number) {
    this.snackBar.open(msg, 'x', {
      duration: responseCode === this.RESPONSE_OK ? 3000 : 20000,
      verticalPosition: this.verticalPosition,
      panelClass: responseCode === this.RESPONSE_OK ? 'back-green' : 'back-red',
    });
  }
}
