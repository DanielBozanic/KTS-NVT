import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import {
  API_GET_ALL_ITEMS,
  API_GET_ITEMS_IN_MENU_BY_CURRENT_PAGE,
  API_GET_ACTIVE_NON_EXPIRED_MENUS,
} from 'src/modules/root/api-routes';
import { Item } from 'src/modules/root/models/item';
import { Menu } from 'src/modules/root/models/menu';

@Injectable({
  providedIn: 'root',
})
export class FoodDrinksManagerService {
  constructor(private http: HttpClient) {}

  getActiveNonExpiredMenus(): Observable<Array<Menu>> {
    return this.http.get<Array<Menu>>(API_GET_ACTIVE_NON_EXPIRED_MENUS);
  }

  getAllItems(): Observable<Array<Item>> {
    return this.http.get<Array<Item>>(API_GET_ALL_ITEMS);
  }

  getItemsInMenuByCurrentPage(
    menuId: number,
    currentPage: number,
    pageSize: number
  ): Observable<Array<Item>> {
    return this.http.get<Array<Item>>(
      `${API_GET_ITEMS_IN_MENU_BY_CURRENT_PAGE}${menuId}?currentPage=${currentPage}&pageSize=${pageSize}`
    );
  }

  errorHander(error: HttpErrorResponse): Observable<any> {
    return throwError(error);
  }
}
