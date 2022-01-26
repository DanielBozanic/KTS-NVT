import { Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatSnackBar, MatSnackBarVerticalPosition } from "@angular/material/snack-bar";
import { MatTableDataSource } from "@angular/material/table";
import { Router } from "@angular/router";
import { Item } from "src/modules/root/models/item";
import { Order } from "src/modules/root/models/order";
import { Table } from "src/modules/root/models/table";
import { WebSocketAPI } from "src/modules/root/WebSocketApi";
import { WaiterTablesService } from "../../services/waiter-tables.service";

@Component({
    selector: 'app-waiter-table',
    templateUrl: './waiter-table.component.html',
    styleUrls: ['./waiter-table.component.scss'],
})
export class WaiterTableComponent implements OnInit {
    @Input() table: Table;
    @Input() webSocketItemChange: WebSocketAPI;
    @Input() webSocketOrders: WebSocketAPI;
    @Output() sentEarlier: EventEmitter<any> = new EventEmitter();
    @Output() getTables: EventEmitter<any> = new EventEmitter();
    currentItems: Item[] = [];
    code!: number;
    itemInOrderDataSource: MatTableDataSource<Item>;
    currentOrder: Order = new Order();
    RESPONSE_OK: number;
    RESPONSE_ERROR: number;
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
        this.itemInOrderDataSource = new MatTableDataSource<Item>(
            this.currentItems
        );
        this.RESPONSE_OK = 0;
        this.RESPONSE_ERROR = -1;
        this.verticalPosition = 'top';
    }

    @ViewChild('freeTableDialog') freeDialog!: TemplateRef<any>;

    @ViewChild('tableOrderDialog') orderDialog!: TemplateRef<any>;

    @ViewChild('paymentTableDialog') paymentDialog!: TemplateRef<any>;

    @ViewChild('codeVerificationDialog') codeDialog!: TemplateRef<any>;

    ngOnInit(): void {
    }

    checkCode(code: number): void {
        this.code = code;
    }

    closeOrderView() {
        this.tableOrderDialog.closeAll();
    }

    async reserveTable() {
        const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
        await dialogRef.afterClosed().toPromise();

        if (this.code) {
            this.service
                .changeTableState(this.table.id, 'RESERVED', this.code)
                .subscribe((data) => {
                    this.getTables.emit(this.table.zoneId);
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

        if (this.code) {
            this.service
                .changeTableState(this.table.id, 'FREE', this.code)
                .subscribe((data) => {
                    this.getTables.emit(this.table.zoneId);

                    if (this.table.orderId)
                        this.service.changeOrderState(this.table.orderId, 'CHARGED', this.code).subscribe((response) => {
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

        if (this.code) {
            this.service
                .changeItemState(id, 'DONE', this.code)
                .subscribe((data) => {

                    this.getTables.emit(this.table.zoneId);
                    if (this.table.orderId)
                        this.service
                            .getItemsForOrder(this.table.orderId)
                            .subscribe((data) => {
                                this.currentItems = data;
                                this.itemInOrderDataSource.data = data;

                                const delivered = this.currentItems.filter(item => item.state === 'DONE').length;
                                const noDelivery = this.currentItems.filter(item => item.state !== 'TO_DELIVER').length;
                                if (delivered === this.currentItems.length) {
                                    this.table.state = 'DONE';
                                    this.getTables.emit(this.table.zoneId);
                                    this.closeOrderView();
                                    window.location.reload();
                                } else if (noDelivery === this.currentItems.length) {
                                    this.table.state = 'IN_PROGRESS';
                                    this.getTables.emit(this.table.zoneId);
                                    this.closeOrderView();
                                    window.location.reload();
                                }

                            });
                    this.openSnackBar("Successfully delivered item", this.RESPONSE_OK)

                }, (error) => {
                    this.openSnackBar(error.error, this.RESPONSE_ERROR);
                });
        }
    }

    async deliverAll() {
        const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
        await dialogRef.afterClosed().toPromise();

        if (this.code && this.table.orderId) {
            this.service.deliverAllItems(this.table.orderId, this.code).subscribe(response => {
                this.openSnackBar("Successfully delivered items", this.RESPONSE_OK)
                this.closeOrderView();
                window.location.reload();
            }, (error) => {
                this.openSnackBar(error.error, this.RESPONSE_ERROR);
            });
            this.code = 0;
        }
    }

    redirectToOrderComponent() {
        this.router.navigate(['/waiterOrder'], { state: { data: this.table } });
    }

    redirectToAddItemsComponent() {
        this.closeOrderView();
        this.router.navigate(['/waiterAddItems'], { state: { data: this.table } });
    }

    async removeItem(id: number) {
        const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
        await dialogRef.afterClosed().toPromise();

        if (this.table.orderId && this.code) {
            this.sentEarlier.emit()
            await this.webSocketOrders._send(`remove-item/${this.table.orderId}/${id}/${this.code}`, {});
            this.code = 0;
        }
    }

    async removeOrder() {
        const dialogRef = this.codeVerificationDialog.open(this.codeDialog);
        await dialogRef.afterClosed().toPromise();

        if (this.table.orderId && this.code) {
            this.sentEarlier.emit()
            await this.webSocketOrders._send(`remove-order/${this.table.orderId}/${this.code}`, {});
            this.code = 0;
        }
    }

    tableColor() {
        if (this.table.state === 'RESERVED') {
            return 'lightgray';
        } else if (this.table.state === 'IN_PROGRESS') {
            return 'orange';
        } else if (this.table.state === 'TO_DELIVER') {
            return 'red';
        } else if (this.table.state === 'FREE') {
            return 'green'
        } else {
            return 'blue';
        }
    }

    tableClick() {
        switch (this.table.state) {
            case 'FREE':
                const dialogRef = this.freeTableDialog.open(this.freeDialog);
                break;

            case 'RESERVED':
                this.redirectToOrderComponent();
                break;

            case 'IN_PROGRESS':
                if (this.table.orderId) {
                    this.service
                        .getItemsForOrder(this.table.orderId)
                        .subscribe((data) => {
                            this.currentItems = data;
                            this.itemInOrderDataSource.data = data;
                            const dialogref = this.tableOrderDialog.open(this.orderDialog, { width: '45em' });
                        });
                }
                break;

            case 'TO_DELIVER':
                if (this.table.orderId) {
                    this.service
                        .getItemsForOrder(this.table.orderId)
                        .subscribe((data) => {
                            this.currentItems = data;
                            this.itemInOrderDataSource.data = data;
                            const dialogref = this.tableOrderDialog.open(this.orderDialog, { width: '45em' });
                        });
                }
                break;

            case 'DONE':
                if (this.table.orderId) {
                    this.service.getOrder(this.table.orderId).subscribe((data) => {
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