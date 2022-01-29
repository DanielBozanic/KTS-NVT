import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {
  API_GET_ALL_ITEMS,
  API_GET_ITEMS_IN_MENU_BY_CURRENT_PAGE,
  API_GET_ACTIVE_NON_EXPIRED_MENUS,
  API_GET_NUMBER_OF_ACTIVE_ITEM_IN_MENU_RECORDS_BY_MENU_ID,
  API_GET_ITEMS_BY_FOOD_TYPE,
  API_GET_ITEMS_BY_SEARCH_TERM,
  API_CREATE_NEW_MENU,
  API_DELETE_MENU,
  API_REMOVE_ITEM_FROM_MENU,
  API_ADD_ITEM_IN_MENU,
  API_CREATE_NEW_ITEM,
  API_GET_ALL_DRINKS,
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

  createNewItem(item: Item): Observable<Item> {
    return this.http
      .post<Item>(API_CREATE_NEW_ITEM, item)
      .pipe(catchError(this.errorHander));
  }

  getItemsByFoodType(foodType: string): Observable<Array<Item>> {
    return this.http.get<Array<Item>>(
      `${API_GET_ITEMS_BY_FOOD_TYPE}${foodType}`
    );
  }

  getItemsBySearchTerm(searchTerm: string): Observable<Array<Item>> {
    return this.http.get<Array<Item>>(
      `${API_GET_ITEMS_BY_SEARCH_TERM}${searchTerm}`
    );
  }

  getDrinks(): Observable<Array<Item>> {
    return this.http.get<Array<Item>>(API_GET_ALL_DRINKS);
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

  getNumberOfActiveItemInMenuRecordsByMenuId(
    menuId: number
  ): Observable<number> {
    return this.http.get<number>(
      `${API_GET_NUMBER_OF_ACTIVE_ITEM_IN_MENU_RECORDS_BY_MENU_ID}${menuId}`
    );
  }

  addItemInMenu(item: Item, menuId: number): Observable<Item> {
    return this.http
      .post<Item>(`${API_ADD_ITEM_IN_MENU}?menuId=${menuId}`, item)
      .pipe(catchError(this.errorHander));
  }

  removeItemFromMenu(itemId: number, menuId: number): Observable<any> {
    return this.http
      .delete(`${API_REMOVE_ITEM_FROM_MENU}?itemId=${itemId}&menuId=${menuId}`)
      .pipe(catchError(this.errorHander));
  }

  addMenu(menu: Menu): Observable<Menu> {
    return this.http
      .post<Menu>(API_CREATE_NEW_MENU, menu)
      .pipe(catchError(this.errorHander));
  }

  deleteMenu(menuId: number): Observable<any> {
    return this.http
      .delete(`${API_DELETE_MENU}${menuId}`)
      .pipe(catchError(this.errorHander));
  }

  errorHander(error: HttpErrorResponse): Observable<any> {
    return throwError(error);
  }
}
