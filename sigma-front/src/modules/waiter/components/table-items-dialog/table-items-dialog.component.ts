import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MatTableDataSource } from "@angular/material/table";
import { Item } from "src/modules/root/models/item";
import { Table } from "src/modules/root/models/table";

@Component({
    selector: 'app-table-items-dialog',
    templateUrl: './table-items-dialog.component.html',
    styleUrls: ['./table-items-dialog.component.scss'],
})
export class TableItemsDialogComponent implements OnInit {
    @Input() currentTable: Table;
    @Input() itemInOrderDataSource: MatTableDataSource<Item>;
    @Output() removeOrder: EventEmitter<any> = new EventEmitter();
    @Output() redirectToAddItemsComponent: EventEmitter<any> = new EventEmitter();
    @Output() removeItem: EventEmitter<any> = new EventEmitter();
    @Output() deliver: EventEmitter<any> = new EventEmitter();
    displayedColumnsItemsInOrder: string[];

    constructor() {
        this.displayedColumnsItemsInOrder = ['name', 'sellingPrice', 'delete'];
    }

    ngOnInit(): void {
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

    removeVisible() {
        let visible = true;
        this.itemInOrderDataSource.data.forEach(item => {
            if (item.state !== 'NEW') {
                visible = false;
            }
        })
        return visible;
    }


}