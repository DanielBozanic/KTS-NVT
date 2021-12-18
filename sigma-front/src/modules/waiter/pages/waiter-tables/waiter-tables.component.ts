import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Item } from 'src/modules/root/models/item';
import { Order } from 'src/modules/root/models/order';
import { Table } from 'src/modules/root/models/table';
import { Zone } from 'src/modules/root/models/zone';
import { WaiterTablesService } from '../../services/waiter-tables.service';

@Component({
  selector: 'app-waiter-tables',
  templateUrl: './waiter-tables.component.html',
  styleUrls: ['./waiter-tables.component.scss'],
})
export class WaiterTablesComponent implements OnInit {
  constructor(
    private service: WaiterTablesService,
    private freeTableDialog: MatDialog,
    private tableOrderDialog: MatDialog,
    private paymentTableDialog: MatDialog,
    private router: Router
  ) {
    this.displayedColumnsItemsInOrder = ['name', 'sellingPrice', 'delete'];
    this.itemInOrderDataSource = new MatTableDataSource<Item>(
      this.currentItems
    );
  }

  
  zones: Zone[] = [];
  tables: Table[] = [];
  currentItems: Item[] = [];
  currentTable!: Table;
  displayedColumnsItemsInOrder: string[];
  itemInOrderDataSource: MatTableDataSource<Item>;
  currentOrder: Order = {
    id: 0,
    state: '',
    totalPrice: 0,
    items: [],
    tableId: 0
  };
  zoneId: number = 0;

  @ViewChild('freeTableDialog') freeDialog!: TemplateRef<any>;

  @ViewChild('tableOrderDialog') orderDialog!: TemplateRef<any>;

  @ViewChild('paymentTableDialog') paymentDialog!: TemplateRef<any>;

  ngOnInit(): void {
    this.service.getAllZones().subscribe((data) => {
      this.zones = data;
    });
  }

  getTables(id: number): void {
    this.service.getTablesForZone(id).subscribe((data) => {
      this.tables = data;
      this.zoneId = id;
    });
  }

  closeOrderView() {
    this.tableOrderDialog.closeAll();
  }

  closePaymentView() {
    this.paymentTableDialog.closeAll();
  }

  reserveTable() {
    this.service
      .changeTableState(this.currentTable.id, 'RESERVED', 1234)
      .subscribe((data) => this.getTables(this.zoneId));
    this.freeTableDialog.closeAll();
  }

  order() {
    this.freeTableDialog.closeAll();
    this.redirectToOrderComponent();
  }

  pay() {
    this.service
      .changeTableState(this.currentTable.id, 'FREE', 1234)
      .subscribe((data) => this.getTables(this.zoneId));
    this.paymentTableDialog.closeAll();
  }

  redirectToOrderComponent() {
    this.router.navigate(['/waiterOrder'], {state: {data: this.currentTable}});
  }

  itemColor(state: string) {
    if (state === 'NEW') {
      return 'lightgray';
    } else if (state === 'IN_PROGRESS') {
      return 'orange';
    } else if (state === 'TO_DELIVER') {
      return 'red';
    } else {
      return 'blue';
    }
  }

  tableClick(table: Table) {
    this.currentTable = table;
    switch (table.state) {
      case 'FREE':
        const dialogRef = this.freeTableDialog.open(this.freeDialog);
        break;

      case 'RESERVED':
        this.redirectToOrderComponent();
        break;

      case 'IN_PROGRESS':
        if (table.orderId) {
          this.service
            .getItemsForOrder(table.orderId)
            .subscribe((data) => {
              this.currentItems = data;
              this.itemInOrderDataSource.data = data;
              const dialogref = this.tableOrderDialog.open(this.orderDialog, {width: '45em'});
            });
        }
        break;

      case 'TO_DELIVER':
        if (table.orderId) {
          this.service
            .getItemsForOrder(table.orderId)
            .subscribe((data) => {
              this.currentItems = data;
              const dialogref = this.tableOrderDialog.open(this.orderDialog, {width: '45em'});
            });
        }
        break;

      case 'DONE':
        if (this.tables[0].orderId) {
          this.service.getOrder(this.tables[0].orderId).subscribe((data) => {
            this.currentOrder = data;
            const dialogref = this.paymentTableDialog.open(this.paymentDialog);
          });
        }
        break;
    }
  }
}
