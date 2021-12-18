import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import {
  MatSnackBar,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Item } from 'src/modules/root/models/item';
import { Menu } from 'src/modules/root/models/menu';
import { Order } from 'src/modules/root/models/order';
import { Table } from 'src/modules/root/models/table';
import { WaiterOrderService } from '../../services/waiter-order.service';

@Component({
  selector: 'app-waiter-order',
  templateUrl: './waiter-order.component.html',
  styleUrls: ['./waiter-order.component.scss'],
})
export class WaiterOrderComponent implements OnInit {
  displayedColumnsItemsInOrder: string[];
  items: Array<Item>;
  activeNonExpiredMenus: Array<Menu>;
  itemInOrderDataSource: MatTableDataSource<Item>;
  itemsInOrderData: Array<Item>;
  isLoading: boolean;
  categories: Array<string>;
  types: Array<string>;
  originalItemData: Array<Item>;
  foodIsTrue: boolean;
  searchForm!: FormGroup;
  RESPONSE_OK: number;
  RESPONSE_ERROR: number;
  verticalPosition: MatSnackBarVerticalPosition;
  selectedMenu: Menu;
  table: Table;
  totalPrice: number;

  constructor(
    private foodDrinksService: WaiterOrderService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.items = [];
    this.activeNonExpiredMenus = [];
    this.displayedColumnsItemsInOrder = ['name', 'quantity', 'sellingPrice', 'delete'];
    this.itemsInOrderData = [];
    this.itemInOrderDataSource = new MatTableDataSource<Item>(
      this.itemsInOrderData
    );
    this.selectedMenu = new Menu();
    this.table = new Table();
    this.isLoading = false;
    this.categories = ['ALL', 'APPETISER', 'SALAD', 'MAIN_COURSE', 'DESERT'];
    this.types = ['APPETISER', 'SALAD', 'MAIN_COURSE', 'DESERT'];
    this.originalItemData = [];
    this.RESPONSE_OK = 0;
    this.RESPONSE_ERROR = -1;
    this.verticalPosition = 'top';
    this.foodIsTrue = true;
    this.totalPrice = 0;
  }

  ngOnInit(): void {
    this.table = history.state.data;
    this.initializeForms();
    this.getActiveNonExpiredMenus();
  }

  initializeForms(): void {
    this.searchForm = new FormGroup({
      searchTerm: new FormControl(''),
    });
  }

  getActiveNonExpiredMenus(): void {
    this.foodDrinksService.getActiveNonExpiredMenus().subscribe((data) => {
      this.activeNonExpiredMenus = data;
      if (data.length !== 0) {
        this.selectedMenu = data[0];
        this.getAllItems();
      } else {
        this.selectedMenu = new Menu();
        this.itemInOrderDataSource.data = [];
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

  addItemToOrder(item : Item){
    let i = this.itemsInOrderData.find(food => food.itemId === item.id);
    if(i){
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

  increase(id : number){
    let item = this.itemsInOrderData.find(i => i.itemId === id);
    if(item){
      item.quantity++;
      this.itemInOrderDataSource.data = this.itemsInOrderData;
      this.calculateTotal()
    }
  }

  decrease(id : number){
    let item = this.itemsInOrderData.find(i => i.itemId === id);
    if(item && item.quantity !== 1){
      item.quantity--;
      this.itemInOrderDataSource.data = this.itemsInOrderData;
      this.calculateTotal()
    }
  }

  removeItemFromOrder(id : number){
    this.itemsInOrderData  = this.itemsInOrderData.filter(item => item.itemId !== id)
    this.itemInOrderDataSource.data = this.itemsInOrderData;
    this.calculateTotal()
  }

  createOrder(){
    if(this.itemsInOrderData.length === 0){
      this.openSnackBar('Order has to have at least 1 item', this.RESPONSE_ERROR);
      return;
    }

    let order = new Order();
    order.items = this.itemsInOrderData;
    order.tableId = this.table.id;
    this.foodDrinksService.createOrder(order, 1234).subscribe(
      response => {
        this.cancel();
      }
    )
  }

  cancel(){
    this.router.navigate(['/waiterTables']);
  }

  calculateTotal(){
    let total = 0;
    this.itemsInOrderData.forEach(item => total += item.sellingPrice  * item.quantity);
    this.totalPrice = total;
  }

  openSnackBar(msg: string, responseCode: number) {
    this.snackBar.open(msg, 'x', {
      duration: responseCode === this.RESPONSE_OK ? 3000 : 20000,
      verticalPosition: this.verticalPosition,
      panelClass: responseCode === this.RESPONSE_OK ? 'back-green' : 'back-red',
    });
  }
}
