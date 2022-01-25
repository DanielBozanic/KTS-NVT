import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Item } from 'src/modules/root/models/item';
import { Menu } from 'src/modules/root/models/menu';
import { WaiterOrderService } from '../../services/waiter-order.service';

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
  categories: Array<string>;

  constructor(private waiterOrderService: WaiterOrderService) {
    this.items = [];
    this.activeNonExpiredMenus = [];
    this.selectedMenu = new Menu();
    this.categories = ['ALL', 'APPETISER', 'SALAD', 'MAIN_COURSE', 'DESERT'];
    this.originalItemData = [];
  }

  ngOnInit(): void {
    this.searchForm = new FormGroup({
      searchTerm: new FormControl(''),
    });
    this.getActiveNonExpiredMenus();
  }

  getActiveNonExpiredMenus(): void {
    this.waiterOrderService.getActiveNonExpiredMenus().subscribe((data) => {
      this.activeNonExpiredMenus = data;
      if (data.length !== 0) {
        this.selectedMenu = data[0];
        this.getAllItems();
      } else {
        this.selectedMenu = new Menu();
      }
    });
  }

  changeMenu(menuId: number): void {
    this.selectedMenu = this.activeNonExpiredMenus.filter(
      (m) => m.id === menuId
    )[0];
    this.waiterOrderService
      .getAllItems(this.selectedMenu.id)
      .subscribe((data) => {
        this.items = data;
        this.originalItemData = data;
      });
  }

  getAllItems(): void {
    this.waiterOrderService
      .getAllItems(this.selectedMenu.id)
      .subscribe((data) => {
        this.items = data;
        this.originalItemData = data;
      });
  }

  filterByCategory(category: string): void {
    if (category === 'ALL') {
      this.items = this.originalItemData;
    } else {
      this.waiterOrderService
        .getItemsByFoodType(this.selectedMenu.id, category)
        .subscribe((data) => {
          this.items = data;
        });
    }
  }

  search(): void {
    if (this.searchForm.get('searchTerm')?.value !== '') {
      this.waiterOrderService
        .getItemsBySearchTerm(
          this.selectedMenu.id,
          this.searchForm.get('searchTerm')?.value
        )
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
