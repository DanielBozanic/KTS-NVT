import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {
  API_GET_ITEMS_IN_MENU,
  API_POST_ORDER,
  API_GET_ITEMS_IN_MENU_BY_CURRENT_PAGE,
  API_GET_ACTIVE_NON_EXPIRED_MENUS,
  API_GET_ITEMS_IN_MENU_BY_FOOD_TYPE,
  API_GET_ITEMS_IN_MENU_BY_SEARCH_TERM,
} from 'src/modules/root/api-routes';
import { Item } from 'src/modules/root/models/item';
import { Menu } from 'src/modules/root/models/menu';
import { Order } from 'src/modules/root/models/order';

@Injectable({
  providedIn: 'root',
})
export class WaiterOrderService {
  constructor(private http: HttpClient) {}

  getActiveNonExpiredMenus(): Observable<Array<Menu>> {
    return this.http.get<Array<Menu>>(API_GET_ACTIVE_NON_EXPIRED_MENUS);
  }

  getAllItems(id : number): Observable<Array<Item>> {
    return this.http.get<Array<Item>>(API_GET_ITEMS_IN_MENU + id);
  }

  getItemsByFoodType(id: number, foodType: string): Observable<Array<Item>> {
    return this.http.get<Array<Item>>(
      `${API_GET_ITEMS_IN_MENU_BY_FOOD_TYPE}${foodType}?menuId=${id}`
    );
  }

  getItemsBySearchTerm(id: number, searchTerm: string): Observable<Array<Item>> {
    return this.http.get<Array<Item>>(
      `${API_GET_ITEMS_IN_MENU_BY_SEARCH_TERM}${searchTerm}?menuId=${id}`
    );
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

  createOrder(order: Order, code: number): Observable<Order>{
    return this.http.post<Order>(`${API_POST_ORDER}${code}`, order)
    .pipe(catchError(this.errorHander));
  }

  errorHander(error: HttpErrorResponse): Observable<any> {
    return throwError(error);
  }
}
