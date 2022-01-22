import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {
  MatSnackBar,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Item } from 'src/modules/root/models/item';
import { NotificationDTO } from 'src/modules/root/models/notification';
import { Order } from 'src/modules/root/models/order';
import { Table } from 'src/modules/root/models/table';
import { WebSocketAPI } from 'src/modules/root/WebSocketApi';

@Component({
  selector: 'app-waiter-order',
  templateUrl: './waiter-order.component.html',
  styleUrls: ['./waiter-order.component.scss'],
})
export class WaiterOrderComponent implements OnInit, OnDestroy {
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
    private webSocketOrderCreation: WebSocketAPI) {
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

  async createOrder(items: Array<Item>) {
    console.log(items);
    if (items.length === 0) {
      this.openSnackBar('Order has to have at least 1 item', this.RESPONSE_ERROR);
      return;
    }

    const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
    await dialogRef.afterClosed().toPromise();

    if (this.code) {
      let order = new Order();
      order.items = items;
      order.tableId = this.table.id;
      this.sentRequestEarlier = true;
      await this.webSocketOrderCreation._send('order-creation/' + this.code, order);
      this.code = 0;
    }
  }

  handleOrderCreation = (notification: NotificationDTO) => {
    if (notification.success) {
      this.router.navigate(['/waiterTables']);
      if (this.sentRequestEarlier) {
        this.openSnackBar('Successfully created order', this.RESPONSE_OK);
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
