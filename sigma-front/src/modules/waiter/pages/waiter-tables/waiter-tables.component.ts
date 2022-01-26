import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { NotifierService } from 'angular-notifier';
import { Globals } from 'src/modules/root/globals';
import { NotificationDTO } from 'src/modules/root/models/notification';
import { Table } from 'src/modules/root/models/table';
import { Zone } from 'src/modules/root/models/zone';
import { WebSocketAPI } from 'src/modules/root/WebSocketApi';
import { WaiterTablesService } from '../../services/waiter-tables.service';

@Component({
  selector: 'app-waiter-tables',
  templateUrl: './waiter-tables.component.html',
  styleUrls: ['./waiter-tables.component.scss'],
})
export class WaiterTablesComponent implements OnInit {
  RESPONSE_OK: number;
  RESPONSE_ERROR: number;
  zonesForm!: FormGroup;
  zones: Zone[] = [];
  tables: Table[] = [];
  zoneId!: number;
  verticalPosition: MatSnackBarVerticalPosition;
  sentRequestEarlier: boolean = false;

  constructor(
    private service: WaiterTablesService,
    private tableOrderDialog: MatDialog,
    private snackBar: MatSnackBar,
    public webSocketItemChange: WebSocketAPI,
    public webSocketOrders: WebSocketAPI,
    private notifier: NotifierService,
    private globals: Globals,
  ) {
    this.RESPONSE_OK = 0;
    this.RESPONSE_ERROR = -1;
    this.verticalPosition = 'top';
  }

  @ViewChild('freeTableDialog') freeDialog!: TemplateRef<any>;

  @ViewChild('tableOrderDialog') orderDialog!: TemplateRef<any>;

  @ViewChild('paymentTableDialog') paymentDialog!: TemplateRef<any>;

  @ViewChild('codeVerificationDialog') codeDialog!: TemplateRef<any>;

  ngOnInit(): void {
    this.zonesForm = new FormGroup({
      zoneSelect: new FormControl()
    })
    this.webSocketItemChange = new WebSocketAPI()
    this.webSocketItemChange._connect('item', this.handleOrderChange);
    this.webSocketOrders = new WebSocketAPI()
    this.webSocketOrders._connect('order', this.handleOrderChange);
    this.service.getAllZones().subscribe((data) => {
      this.zones = data;
      this.zoneId = this.zones[0].id;
      this.zonesForm.get('zoneSelect')?.setValue(this.zoneId);
      this.getTables(this.zoneId);
    });
  }

  ngOnDestroy(): void {
    this.webSocketItemChange._disconnect();
    this.webSocketOrders._disconnect();
  }

  getTables(id: number): void {
    this.service.getTablesForZone(id).subscribe((data) => {
      this.tables = data;
      this.zoneId = id;
    });
  }

  sentEarlier(): void {
    this.sentRequestEarlier = true;
  }

  handleOrderChange = (notification: NotificationDTO) => {
    if (notification.success) {
      if (this.sentRequestEarlier) {
        this.openSnackBar(notification.message, this.RESPONSE_OK);
        this.tableOrderDialog.closeAll();
      } else {
        this.notifier.notify('info', notification.message);
        this.globals.waiterNotifications++;
      }
      this.getTables(this.zoneId);
    } else {
      if (this.sentRequestEarlier) {
        this.openSnackBar(notification.message, this.RESPONSE_ERROR);
      }
    }
    this.sentRequestEarlier = false;
  }

  pageClick() {
    this.globals.waiterNotifications = 0;
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
