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
  API_GET_ORDER,
  API_GET_ALL_FOOD_ORDERS,
  API_GET_ITEM_IN_ORDER,
  API_CHANGE_TABLE_STATE,
  API_GET_EMPLOYEE,
  API_GET_ALL_DRINK_ORDERS,
} from 'src/modules/root/api-routes';
import { Employee } from 'src/modules/root/models/employee';
import { Item } from 'src/modules/root/models/item';
import { Menu } from 'src/modules/root/models/menu';
import { Order } from 'src/modules/root/models/order';
import { Table } from 'src/modules/root/models/table';

@Injectable({
  providedIn: 'root',
})
export class CookBartenderService {
  constructor(private http: HttpClient) { }

  getAllFoodOrders(): Observable<Array<Order>> {
    return this.http.get<Array<Order>>(API_GET_ALL_FOOD_ORDERS);
  }

  getAllDrinkOrders(): Observable<Array<Order>> {
    return this.http.get<Array<Order>>(API_GET_ALL_DRINK_ORDERS);
  }

  setOrderState(OrderId: number, NewState: string): Observable<void> {
    return this.http.put<any>(API_GET_ORDER + "changeWithoutCode/" + OrderId + "/" + NewState + "/", "");
  }

  setItemState(OrderId: number, NewState: string, Code: number): Observable<void> {
    return this.http.put<any>(API_GET_ITEM_IN_ORDER + OrderId + "/" + NewState + "/" + Code + "/", "");
  }

  getEmployee(id: number): Observable<Employee> {
    return this.http.get<Employee>(API_GET_EMPLOYEE + id);
  }

  getTableFromId(TableId: number): Observable<Table> {
    return this.http.get<Table>(API_CHANGE_TABLE_STATE + TableId);
  }

  getActiveNonExpiredMenus(): Observable<Array<Menu>> {
    return this.http.get<Array<Menu>>(API_GET_ACTIVE_NON_EXPIRED_MENUS);
  }

  getAllItems(id: number): Observable<Array<Item>> {
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

  createOrder(order: Order, code: number): Observable<Order> {
    return this.http.post<Order>(`${API_POST_ORDER}${code}`, order)
      .pipe(catchError(this.errorHander));
  }

  errorHander(error: HttpErrorResponse): Observable<any> {
    return throwError(error);
  }
}
