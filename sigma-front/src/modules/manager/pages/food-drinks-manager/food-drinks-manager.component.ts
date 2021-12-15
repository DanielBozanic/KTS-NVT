import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Item } from 'src/modules/root/models/item';
import { Menu } from 'src/modules/root/models/menu';
import { FoodDrinksManagerService } from '../../services/food-drinks-manager.service';

@Component({
  selector: 'app-food-drinks-manager',
  templateUrl: './food-drinks-manager.component.html',
  styleUrls: ['./food-drinks-manager.component.scss'],
})
export class FoodDrinksManagerComponent implements OnInit {
  displayedColumnsItemsInMenu: string[];
  items: Array<Item>;
  activeNonExpiredMenus: Array<Menu>;
  currentPage: number;
  pageSize: number;
  totalRows: number;
  itemInMenuDataSource: MatTableDataSource<Item>;
  itemsInMenuData: Array<Item>;
  isLoading: boolean;
  selectedMenu!: Menu;

  constructor(private foodDrinksService: FoodDrinksManagerService) {
    this.currentPage = 0;
    this.pageSize = 10;
    this.totalRows = 0;
    this.items = [];
    this.activeNonExpiredMenus = [];
    this.displayedColumnsItemsInMenu = ['name', 'sellingPrice', 'delete'];
    this.itemsInMenuData = [];
    this.itemInMenuDataSource = new MatTableDataSource<Item>(
      this.itemsInMenuData
    );
    this.isLoading = false;
  }

  ngOnInit(): void {
    this.getActiveNonExpiredMenus();
    this.getAllItems();
  }

  ngAfterViewInit(): void {
    this.itemInMenuDataSource.paginator = this.paginatorItemsInMenu;
  }

  @ViewChild(MatPaginator)
  paginatorItemsInMenu!: MatPaginator;

  getActiveNonExpiredMenus(): void {
    this.foodDrinksService.getActiveNonExpiredMenus().subscribe((data) => {
      this.activeNonExpiredMenus = data;
      this.selectedMenu = data[0];
      this.getItemsInMenuByCurrentPage(data[0].id, false);
    });
  }

  getAllItems() {
    this.foodDrinksService.getAllItems().subscribe((data) => {
      this.items = data;
    });
  }

  getItemsInMenuByCurrentPage(menuId: number, deleting: boolean): void {
    this.isLoading = true;
    this.foodDrinksService
      .getItemsInMenuByCurrentPage(menuId, this.currentPage, this.pageSize)
      .subscribe((data) => {
        const itemsInMenu = data;
        this.itemsInMenuData = itemsInMenu;
        this.itemInMenuDataSource.data = itemsInMenu;
        setTimeout(() => {
          if (itemsInMenu.length === 0 && deleting) {
            const setPrev = this.currentPage - 1;
            this.currentPage = setPrev;
            this.paginatorItemsInMenu.pageIndex = setPrev;
            this.getItemsInMenuByCurrentPage(menuId, false);
          } else {
            this.paginatorItemsInMenu.pageIndex = this.currentPage;
          }
          this.paginatorItemsInMenu.length = this.totalRows;
        });
        this.isLoading = false;
      });
  }

  changeMenu(menuId: number): void {
    this.selectedMenu = this.activeNonExpiredMenus.filter(
      (m) => m.id === menuId
    )[0];
    this.currentPage = 0;
    this.getItemsInMenuByCurrentPage(menuId, false);
  }

  addItemToMenu(itemId: number): void {
    console.log(itemId);
  }

  removeItemFromMenu(id: number): void {
    console.log(id);
  }

  pageChanged(event: PageEvent): void {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.getItemsInMenuByCurrentPage(this.selectedMenu.id, false);
  }
}
