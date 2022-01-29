import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import {
  MatSnackBar,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
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
  selectedMenu: Menu;
  selectedItem: Item;
  categories: Array<string>;
  originalItemData: Array<Item>;
  searchForm!: FormGroup;
  RESPONSE_OK: number;
  RESPONSE_ERROR: number;
  verticalPosition: MatSnackBarVerticalPosition;

  constructor(
    private foodDrinksService: FoodDrinksManagerService,
    private snackBar: MatSnackBar,
    private createNewMenuDialog: MatDialog,
    private addItemInMenuDialog: MatDialog,
    private createNewItemDialog: MatDialog
  ) {
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
    this.selectedMenu = new Menu();
    this.selectedItem = new Item();
    this.categories = [
      'ALL',
      'APPETISER',
      'SALAD',
      'MAIN_COURSE',
      'DESERT',
      'DRINKS',
    ];
    this.originalItemData = [];
    this.RESPONSE_OK = 0;
    this.RESPONSE_ERROR = -1;
    this.verticalPosition = 'top';
  }

  ngOnInit(): void {
    this.initializeForms();
    this.getActiveNonExpiredMenus();
    this.getAllItems();
  }

  ngAfterViewInit(): void {
    this.itemInMenuDataSource.paginator = this.paginatorItemsInMenu;
  }

  @ViewChild(MatPaginator)
  paginatorItemsInMenu!: MatPaginator;

  @ViewChild('menuDialog') menuDialog!: TemplateRef<any>;

  @ViewChild('itemInMenuDialog') itemInMenuDialog!: TemplateRef<any>;

  @ViewChild('itemDialog') itemDialog!: TemplateRef<any>;

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
        this.getNumberOfActiveItemInMenuRecordsByMenuId(data[0].id);
        this.getItemsInMenuByCurrentPage(data[0].id, false);
      } else {
        this.selectedMenu = new Menu();
        this.currentPage = 0;
        this.totalRows = 0;
        this.itemInMenuDataSource.data = [];
      }
    });
  }

  getAllItems(): void {
    this.foodDrinksService.getAllItems().subscribe((data) => {
      console.log(data);
      this.items = data;
      this.originalItemData = data;
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
            if (this.currentPage !== 0) {
              const setPrev = this.currentPage - 1;
              this.currentPage = setPrev;
              this.paginatorItemsInMenu.pageIndex = setPrev;
            }
            this.getItemsInMenuByCurrentPage(menuId, false);
          } else {
            this.paginatorItemsInMenu.pageIndex = this.currentPage;
          }
          this.paginatorItemsInMenu.length = this.totalRows;
        });
        this.isLoading = false;
      });
  }

  getNumberOfActiveItemInMenuRecordsByMenuId(menuId: number): void {
    this.foodDrinksService
      .getNumberOfActiveItemInMenuRecordsByMenuId(menuId)
      .subscribe((data) => {
        this.totalRows = data;
      });
  }

  changeMenu(menuId: number): void {
    this.selectedMenu = this.activeNonExpiredMenus.filter(
      (m) => m.id === menuId
    )[0];
    this.currentPage = 0;
    this.getNumberOfActiveItemInMenuRecordsByMenuId(menuId);
    this.getItemsInMenuByCurrentPage(menuId, false);
  }

  openAddItemToMenuDialog(item: Item): void {
    this.selectedItem = item;
    this.addItemInMenuDialog.open(this.itemInMenuDialog);
  }

  addItemInMenu(item: Item): void {
    item.id = this.selectedItem.id;
    this.foodDrinksService.addItemInMenu(item, this.selectedMenu.id).subscribe(
      (resp) => {
        console.log(resp);
        this.addItemInMenuDialog.closeAll();
        this.getNumberOfActiveItemInMenuRecordsByMenuId(this.selectedMenu.id);
        this.getItemsInMenuByCurrentPage(this.selectedMenu.id, false);
      },
      (err) => {
        this.openSnackBar(err.error, this.RESPONSE_ERROR);
      }
    );
  }

  removeItemFromMenu(itemId: number): void {
    this.foodDrinksService
      .removeItemFromMenu(itemId, this.selectedMenu.id)
      .subscribe(
        (data) => {
          this.getNumberOfActiveItemInMenuRecordsByMenuId(this.selectedMenu.id);
          this.getItemsInMenuByCurrentPage(this.selectedMenu.id, true);
        },
        (err) => {
          this.openSnackBar(err.error, this.RESPONSE_ERROR);
        }
      );
  }

  openCreateNewItemDialog(): void {
    this.createNewItemDialog.open(this.itemDialog);
  }

  createNewItem(item: Item): void {
    this.foodDrinksService.createNewItem(item).subscribe(
      (resp) => {
        console.log(resp);
        this.createNewItemDialog.closeAll();
        this.getAllItems();
      },
      (err) => {
        this.openSnackBar(err.error, this.RESPONSE_ERROR);
      }
    );
  }

  openCreateNewMenuDialog(): void {
    this.createNewMenuDialog.open(this.menuDialog);
  }

  createNewMenu(menu: Menu): void {
    this.foodDrinksService.addMenu(menu).subscribe(
      (resp) => {
        console.log(resp);
        this.createNewMenuDialog.closeAll();
        this.getActiveNonExpiredMenus();
      },
      (err) => {
        this.openSnackBar(err.error, this.RESPONSE_ERROR);
      }
    );
  }

  deleteSelectedMenu(): void {
    this.foodDrinksService.deleteMenu(this.selectedMenu.id).subscribe(
      (resp) => {
        this.getActiveNonExpiredMenus();
      },
      (err) => {
        this.openSnackBar(err.error, this.RESPONSE_ERROR);
      }
    );
  }

  filterByCategory(category: string): void {
    if (category === 'ALL') {
      this.items = this.originalItemData;
    } else if (category === 'DRINKS') {
      this.foodDrinksService.getDrinks().subscribe((data) => {
        this.items = data;
      });
    } else {
      this.foodDrinksService.getItemsByFoodType(category).subscribe((data) => {
        this.items = data;
      });
    }
  }

  search(): void {
    if (this.searchForm.get('searchTerm')?.value !== '') {
      this.foodDrinksService
        .getItemsBySearchTerm(this.searchForm.get('searchTerm')?.value)
        .subscribe((data) => {
          this.items = data;
        });
    }
  }

  resetSearchFilter(): void {
    this.items = this.originalItemData;
    this.searchForm.patchValue({ searchTerm: '' });
  }

  pageChanged(event: PageEvent): void {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.getItemsInMenuByCurrentPage(this.selectedMenu.id, false);
  }

  openSnackBar(msg: string, responseCode: number) {
    this.snackBar.open(msg, 'x', {
      duration: responseCode === this.RESPONSE_OK ? 3000 : 20000,
      verticalPosition: this.verticalPosition,
      panelClass: responseCode === this.RESPONSE_OK ? 'back-green' : 'back-red',
    });
  }
}
