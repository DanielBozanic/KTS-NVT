import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MatTableDataSource } from "@angular/material/table";
import { Router } from "@angular/router";
import { Item } from "src/modules/root/models/item";
import { Table } from "src/modules/root/models/table";

@Component({
    selector: 'app-item-addition',
    templateUrl: './item-addition.component.html',
    styleUrls: ['./item-addition.component.scss'],
})
export class ItemAdditionComponent implements OnInit {
    @Input() table: Table;
    @Input() actionButtonText: string;
    @Output() order: EventEmitter<any> = new EventEmitter();
    displayedColumnsItemsInOrder: string[];
    itemInOrderDataSource: MatTableDataSource<Item>;
    itemsInOrderData: Array<Item>;
    isLoading: boolean;
    totalPrice: number;

    constructor(private router: Router,) {
        this.displayedColumnsItemsInOrder = ['name', 'quantity', 'sellingPrice', 'delete'];
        this.itemsInOrderData = [];
        this.itemInOrderDataSource = new MatTableDataSource<Item>(
            this.itemsInOrderData
        );
        this.isLoading = false;
        this.totalPrice = 0;
    }

    ngOnInit(): void {
        this.itemInOrderDataSource.data = [];
    }

    addItemToOrder(item: Item) {
        let i = this.itemsInOrderData.find(food => food.itemId === item.id);
        if (i) {
            i.quantity++;
            this.itemInOrderDataSource.data = this.itemsInOrderData;
            this.calculateTotal()
            return;
        }
        let itemInOrder = new Item();
        itemInOrder.itemId = item.id;
        itemInOrder.quantity = 1;
        itemInOrder.sellingPrice = item.sellingPrice;
        itemInOrder.name = item.name;

        this.itemsInOrderData.push(itemInOrder);
        this.itemInOrderDataSource.data = this.itemsInOrderData;
        this.calculateTotal()
    }

    increase(id: number) {
        let item = this.itemsInOrderData.find(i => i.itemId === id);
        if (item) {
            item.quantity++;
            this.itemInOrderDataSource.data = this.itemsInOrderData;
            this.calculateTotal()
        }
    }

    decrease(id: number) {
        let item = this.itemsInOrderData.find(i => i.itemId === id);
        if (item && item.quantity !== 1) {
            item.quantity--;
            this.itemInOrderDataSource.data = this.itemsInOrderData;
            this.calculateTotal()
        }
    }

    removeItemFromOrder(id: number) {
        this.itemsInOrderData = this.itemsInOrderData.filter(item => item.itemId !== id)
        this.itemInOrderDataSource.data = this.itemsInOrderData;
        this.calculateTotal()
    }

    calculateTotal() {
        let total = 0;
        this.itemsInOrderData.forEach(item => total += item.sellingPrice * item.quantity);
        this.totalPrice = total;
    }

    cancel() {
        this.router.navigate(['/waiterTables']);
    }
}