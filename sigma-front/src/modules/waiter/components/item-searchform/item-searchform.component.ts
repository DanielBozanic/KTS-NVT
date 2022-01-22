import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MatTableDataSource } from "@angular/material/table";
import { Item } from "src/modules/root/models/item";
import { Menu } from "src/modules/root/models/menu";
import { Table } from "src/modules/root/models/table";
import { WaiterOrderService } from "../../services/waiter-order.service";

@Component({
    selector: 'app-item-searchform',
    templateUrl: './item-searchform.component.html',
    styleUrls: ['./item-searchform.component.scss'],
})
export class ItemSearchformComponent implements OnInit {
    @Output() addItemToOrder: EventEmitter<any> = new EventEmitter();
    items: Array<Item>;
    activeNonExpiredMenus: Array<Menu>;
    selectedMenu: Menu;
    originalItemData: Array<Item>;
    searchForm!: FormGroup;
    foodIsTrue: boolean;
    isLoading: boolean;
    categories: Array<string>;
    types: Array<string>;


    constructor(
        private foodDrinksService: WaiterOrderService,
    ) {
        this.items = [];
        this.activeNonExpiredMenus = [];
        this.selectedMenu = new Menu();
        this.isLoading = false;
        this.categories = ['ALL', 'APPETISER', 'SALAD', 'MAIN_COURSE', 'DESERT'];
        this.types = ['APPETISER', 'SALAD', 'MAIN_COURSE', 'DESERT'];
        this.originalItemData = [];
        this.foodIsTrue = true;
    }

    ngOnInit(): void {
        this.searchForm = new FormGroup({
            searchTerm: new FormControl(''),
        });
        this.getActiveNonExpiredMenus();
    }

    getActiveNonExpiredMenus(): void {
        this.foodDrinksService.getActiveNonExpiredMenus().subscribe((data) => {
            this.activeNonExpiredMenus = data;
            if (data.length !== 0) {
                this.selectedMenu = data[0];
                this.getAllItems();
            } else {
                this.selectedMenu = new Menu();
            }
        });
    }

    getAllItems(): void {
        this.foodDrinksService.getAllItems(this.selectedMenu.id).subscribe((data) => {
            this.items = data;
            this.originalItemData = data;
        });
    }

    filterByCategory(category: string): void {
        if (category === 'ALL') {
            this.items = this.originalItemData;
        } else {
            this.foodDrinksService.getItemsByFoodType(this.selectedMenu.id, category).subscribe((data) => {
                this.items = data;
            });
        }
    }

    search(): void {
        if (this.searchForm.get('searchTerm')?.value !== '') {
            this.foodDrinksService
                .getItemsBySearchTerm(this.selectedMenu.id, this.searchForm.get('searchTerm')?.value)
                .subscribe((data) => {
                    this.items = data;
                });
        }
    }

    resetSearchFilter(): void {
        this.items = this.originalItemData;
        this.searchForm.patchValue({ searchTerm: '' });
    }


}