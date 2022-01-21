import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import {
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {
  MatSnackBar,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { Subject } from 'rxjs';
import { GroupedOrder } from 'src/modules/root/models/groupedOrder';
import { Item } from 'src/modules/root/models/item';
import { Menu } from 'src/modules/root/models/menu';
import { Order } from 'src/modules/root/models/order';
import { Table } from 'src/modules/root/models/table';
import { CookBartenderService } from '../../services/cook&bartender.service';
import { NotificationDTO } from 'src/modules/root/models/notification';
import { WebSocketAPI } from 'src/modules/root/WebSocketApi';
import { NotifierService } from 'angular-notifier';
import { Globals } from 'src/modules/root/globals';

@Component({
  selector: 'app-bartender',
  templateUrl: './bartender.component.html',
  styleUrls: ['./bartender.component.scss'],
})
export class BartenderComponent implements OnInit {
  items: Array<Item>;
  newOrders: Array<Order>;
  groupedNewOrders: Array<GroupedOrder>;
  ordersInProgress: Array<Order>;
  validatingForm: FormGroup;
  validCode: boolean;
  code: number;
  RESPONSE_OK: number;
  RESPONSE_ERROR: number;
  verticalPosition: MatSnackBarVerticalPosition;
  ItemsOfWorker: Map<number, string>;
  sentRequestEarlier: boolean = false;

  constructor(
    private service: CookBartenderService,
    private snackBar: MatSnackBar,
    private codeVerificationDialog: MatDialog,
    private webSocketItemChange: WebSocketAPI,
    private webSocketOrderCreation: WebSocketAPI,
    private notifier: NotifierService,
    private globals: Globals,
  ) {
    this.items = [];
    this.newOrders = [];
    this.groupedNewOrders = [];
    this.validCode = false;
    this.ordersInProgress = [];
    this.code = 0;
    this.ItemsOfWorker = new Map();
    this.validatingForm = new FormGroup({
      code: new FormControl('', Validators.required)
    });
    this.RESPONSE_OK = 0;
    this.RESPONSE_ERROR = -1;
    this.verticalPosition = 'top';
  }

  @ViewChild('codeVerificationDialog') codeDialog!: TemplateRef<any>;



  ngOnInit(): void {
    this.getAllOrders();
    this.webSocketItemChange = new WebSocketAPI()
    this.webSocketItemChange._connect('item', this.handleItemChange);
    this.webSocketOrderCreation = new WebSocketAPI()
    this.webSocketOrderCreation._connect('order', this.handleOrderCreation);
  }

  ngOnDestroy(): void {
    this.webSocketOrderCreation._disconnect();
    this.webSocketItemChange._disconnect();
  }

  closeCodeDialog(): void {
    this.codeVerificationDialog.closeAll();
  }

  checkCode(): void {
    let code = this.validatingForm.get('code')?.value;
    this.code = parseInt(code);
    this.validatingForm.reset();
    this.codeVerificationDialog.closeAll();
  }

  get codeFromDialog() {
    return this.validatingForm.get('code') as FormControl;
  }

  getAllOrders(): void {
    this.service.getAllDrinkOrders().subscribe((data) => {
      this.newOrders = [];
      this.ordersInProgress = [];
      this.groupedNewOrders = [];
      this.ItemsOfWorker.clear();
      data.forEach(order => {
        if (order.state == "NEW") {
          this.newOrders.push(order);
        }
        else if (order.state == "IN_PROGRESS")
          this.ordersInProgress.push(order);
      });

      console.log(this.newOrders);
      console.log(this.ordersInProgress);

      this.newOrders.forEach(order => {
        let go = {
          id: order.id,
          table: new Table(),
          state: order.state,
          totalPrice: order.totalPrice,
          itemsByQuantity: new Map()
        };
        order.items.forEach(item => {
          if (go.itemsByQuantity.has(item.name))
            go.itemsByQuantity.set(item.name, go.itemsByQuantity.get(item.name) + 1);
          else
            go.itemsByQuantity.set(item.name, 1);
        });
        this.groupedNewOrders.push(go);
      });

      let i = 0;
      this.newOrders.forEach(order => {
        this.service.getTableFromId(order.tableId).subscribe((table) => {
          this.groupedNewOrders[i].table = table;
          i += 1;
        });
      });

      this.ordersInProgress.forEach(order => {
        order.items.forEach(item => {
          if (item.employeeId) {
            this.service.getEmployee(item.employeeId).subscribe(data => {
              this.ItemsOfWorker.set(item.id, data.name);
            });
          }
        });
      });
    });
  }

  setOrderToInProgress(itemId: number) {
    this.service.setOrderState(itemId, "IN_PROGRESS").subscribe(data => {
      this.openSnackBar("Order moved to current orders", 0);
      this.getAllOrders();
    });
  }

  setOrderToDone(itemId: number) {
    this.service.setOrderState(itemId, "DONE").subscribe(data => {
      this.getAllOrders();
    });
  }

  async setItemStateToInProgress(itemId: number) {
    const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
    await dialogRef.afterClosed().toPromise();

    if (this.code != 0) {
      this.sentRequestEarlier = true;
      await this.webSocketItemChange._send(`item-change/${itemId}/IN_PROGRESS/${this.code}`, {});
      this.code = 0;
    }
  }
  async setItemStateToDeliver(itemId: number, order: Order) {
    const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
    await dialogRef.afterClosed().toPromise();

    if (this.code != 0) {
      this.sentRequestEarlier = true;
      await this.webSocketItemChange._send(`item-change/${itemId}/TO_DELIVER/${this.code}`, {});
      this.code = 0;
    }
  }

  handleItemChange = (notification: NotificationDTO) => {
    if (notification.success) {
      if (this.sentRequestEarlier) {
        this.openSnackBar(notification.message, this.RESPONSE_OK);
      } else if (notification.code === 'drink') {
        this.notifier.notify('info', notification.message);
        this.globals.bartenderNotifications++;
      }
      this.getAllOrders();
    } else {
      if (this.sentRequestEarlier) {
        this.openSnackBar(notification.message, this.RESPONSE_ERROR);
      }
    }
    this.sentRequestEarlier = false;
  }

  handleOrderCreation = (notification: NotificationDTO) => {
    if (notification.success) {
      if (notification.message.includes('removed')) {

        const exists1 = this.newOrders.find(order => order.id === notification.id)
        const exists2 = this.ordersInProgress.find(order => order.id === notification.id)
        if ((exists1 || exists2) && notification.code !== 'food') {
          this.notifier.notify('info', notification.message);
          this.globals.bartenderNotifications++;
        }

      } else {

        this.service.getAllDrinkOrders().subscribe((data) => {
          const exists = data.find(order => order.id === notification.id)
          if ((exists) && notification.code !== 'food') {
            this.notifier.notify('info', notification.message);
            this.globals.bartenderNotifications++;
          }
        })

      }
      this.getAllOrders();
    } else {
      if (this.sentRequestEarlier) {
        this.openSnackBar(notification.message, this.RESPONSE_ERROR);
      }
    }
  }

  pageClick() {
    this.globals.bartenderNotifications = 0;
    this.notifier.hideAll();
  }

  openSnackBar(msg: string, responseCode: number) {
    this.snackBar.open(msg, 'x', {
      duration: responseCode === this.RESPONSE_OK ? 3000 : 20000,
      verticalPosition: this.verticalPosition,
      panelClass: responseCode === this.RESPONSE_OK ? 'back-green' : 'back-red',
    });
  }
}
