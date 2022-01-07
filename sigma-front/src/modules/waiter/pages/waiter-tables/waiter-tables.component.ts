import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
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
  RESPONSE_OK: number;
  RESPONSE_ERROR: number;
  validatingForm: FormGroup;
  zonesForm!: FormGroup;
  zones: Zone[] = [];
  tables: Table[] = [];
  currentItems: Item[] = [];
  currentTable!: Table;
  code!: number;
  displayedColumnsItemsInOrder: string[];
  itemInOrderDataSource: MatTableDataSource<Item>;
  currentOrder: Order = new Order();
  zoneId!: number;
  verticalPosition: MatSnackBarVerticalPosition;

  constructor(
    private service: WaiterTablesService,
    private freeTableDialog: MatDialog,
    private tableOrderDialog: MatDialog,
    private paymentTableDialog: MatDialog,
    private router: Router,
    private codeVerificationDialog: MatDialog,
    private snackBar: MatSnackBar,
  ) {
    this.displayedColumnsItemsInOrder = ['name', 'sellingPrice', 'delete'];
    this.itemInOrderDataSource = new MatTableDataSource<Item>(
      this.currentItems
    );
    this.validatingForm = new FormGroup({
      code: new FormControl('', Validators.required)
    });
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
    this.service.getAllZones().subscribe((data) => {
      this.zones = data;
      this.zoneId = this.zones[0].id;
      this.zonesForm.get('zoneSelect')?.setValue(this.zoneId);
      this.getTables(this.zoneId);
    });
  }

  getTables(id: number): void {
    this.service.getTablesForZone(id).subscribe((data) => {
      this.tables = data;
      this.zoneId = id;
    });
  }

  get codeFromDialog() {
    return this.validatingForm.get('code') as FormControl;
  }

  checkCode(): void {
    this.code = this.validatingForm.get('code')?.value;
    this.validatingForm.reset();
  }

  closeOrderView() {
    this.tableOrderDialog.closeAll();
  }

  async reserveTable() {
    const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
    await dialogRef.afterClosed().toPromise();

    if(this.code){
      this.service
      .changeTableState(this.currentTable.id, 'RESERVED', this.code)
      .subscribe((data) => {
        this.getTables(this.zoneId);
        this.openSnackBar("Successfully reserved table", this.RESPONSE_OK)
      }, (error) => {
        this.openSnackBar(error.error, this.RESPONSE_ERROR);
      });
      this.freeTableDialog.closeAll();
    }
  }

  order() {
    this.freeTableDialog.closeAll();
    this.redirectToOrderComponent();
  }

  async pay() {
    const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
    await dialogRef.afterClosed().toPromise();

    if(this.code){
      this.service
      .changeTableState(this.currentTable.id, 'FREE', this.code)
      .subscribe((data) => {
        this.getTables(this.zoneId);

        if(this.currentTable.orderId)
          this.service.changeOrderState(this.currentTable.orderId, 'CHARGED', this.code).subscribe((response) => {
            this.openSnackBar("Successfully charged order", this.RESPONSE_OK);
          }, (error) => {
            this.openSnackBar(error.error, this.RESPONSE_ERROR);
          });

      }, (error) => {
        this.openSnackBar(error.error, this.RESPONSE_ERROR);
      });
      this.paymentTableDialog.closeAll();
    }
  }

  async deliver(id: number) {
    const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
    await dialogRef.afterClosed().toPromise();

    if(this.code){
      this.service
      .changeItemState(id, 'DONE', this.code)
      .subscribe((data) => {

        this.getTables(this.zoneId);
        if(this.currentTable.orderId)
          this.service
              .getItemsForOrder(this.currentTable.orderId)
              .subscribe((data) => {
                this.currentItems = data;
                this.itemInOrderDataSource.data = data;
                
                const delivered = this.currentItems.filter(item => item.state === 'DONE').length;
                if(delivered === this.currentItems.length){

                  this.service
                  .changeTableState(this.currentTable.id, 'DONE', this.code)
                  .subscribe((data) => {
                    this.getTables(this.zoneId)
                    this.closeOrderView();
                  });
                }

              });
        this.openSnackBar("Successfully delivered item", this.RESPONSE_OK)
      
      }, (error) => {
        this.openSnackBar(error.error, this.RESPONSE_ERROR);
      });
    }
  }

  redirectToOrderComponent() {
    this.router.navigate(['/waiterOrder'], {state: {data: this.currentTable}});
  }

  redirectToAddItemsComponent() {
    this.closeOrderView();
    this.router.navigate(['/waiterAddItems'], {state: {data: this.currentTable}});
  }

  async removeItem(id: number){
    const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
    await dialogRef.afterClosed().toPromise();

    if(this.currentTable.orderId && this.code){
      this.service.removeItemFromOrder(this.currentTable.orderId, this.code, id).subscribe(response =>{
        this.currentItems = this.currentItems.filter(item => item.id !== id)
        this.itemInOrderDataSource.data = this.currentItems;
        const delivered = this.currentItems.filter(item => item.state === 'DONE').length;
                if(delivered === this.currentItems.length){

                  this.service
                  .changeTableState(this.currentTable.id, 'DONE', this.code)
                  .subscribe((data) => {
                    this.getTables(this.zoneId)
                    this.closeOrderView();
                  });
                }
        this.openSnackBar("Successfully removed item from order", this.RESPONSE_OK)
      }, (error) => {
        this.openSnackBar(error.error, this.RESPONSE_ERROR);
      })

      const delivered = this.currentItems.filter(item => item.state === 'DONE').length;
      if(delivered === this.currentItems.length){
        this.service
        .changeTableState(this.currentTable.id, 'DONE', this.code)
        .subscribe((data) => this.getTables(this.zoneId));
      }
    }
  }

  async addItem(item: Item){
    const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
    await dialogRef.afterClosed().toPromise();

    if(this.currentTable.orderId && this.code){
      this.service.addItemToOrder(this.currentTable.orderId, this.code, item).subscribe(response =>{
        this.openSnackBar("Successfully added item to order", this.RESPONSE_OK)
      },
      (error) => {
        this.openSnackBar(error.error, this.RESPONSE_ERROR);
      })
    }
  }

  async removeOrder(){
    const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
    await dialogRef.afterClosed().toPromise();

    if(this.currentTable.orderId && this.code){
      this.service.deleteOrder(this.currentTable.orderId, this.code).subscribe(response =>{
        this.service
        .changeTableState(this.currentTable.id, 'FREE', this.code)
        .subscribe((data) => this.getTables(this.zoneId));
        this.tableOrderDialog.closeAll();
        this.openSnackBar("Successfully removed order", this.RESPONSE_OK)
      },
      (error) => {
        this.openSnackBar(error.error, this.RESPONSE_ERROR);
      })
    }
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

  tableColor(state: string) {
    if (state === 'RESERVED') {
      return 'lightgray';
    } else if (state === 'IN_PROGRESS') {
      return 'orange';
    } else if (state === 'TO_DELIVER') {
      return 'red';
    } else if(state === 'FREE'){
      return 'green'
    } else {
      return 'blue';
    }
  }

  removeVisible(){
    let visible = true;
    this.currentItems.forEach(item => {
      if(item.state !== 'NEW'){
        visible = false;
      }
    })
    return visible;
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
        if (table.orderId) {
          this.service.getOrder(table.orderId).subscribe((data) => {
            this.currentOrder = data;
            const dialogref = this.paymentTableDialog.open(this.paymentDialog);
          });
        }
        break;
    }
  }

  openSnackBar(msg: string, responseCode: number) {
    this.snackBar.open(msg, 'x', {
      duration: responseCode === this.RESPONSE_OK ? 3000 : 20000,
      verticalPosition: this.verticalPosition,
      panelClass: responseCode === this.RESPONSE_OK ? 'back-green' : 'back-red',
    });
  }
}
