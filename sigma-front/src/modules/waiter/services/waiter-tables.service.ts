import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  API_GET_ALL_ZONES,
  API_GET_TABLES_FOR_ZONE,
  API_GET_ITEMS_FOR_ORDER,
  API_CHANGE_TABLE_STATE,
  API_CHANGE_ITEM_STATE,
  API_REMOVE_ITEM_FROM_ORDER,
  API_ADD_ITEM_TO_ORDER,
  API_DELETE_ORDER,
  API_GET_ORDER,
} from 'src/modules/root/api-routes';
import { Order } from 'src/modules/root/models/order';
import { Table } from 'src/modules/root/models/table';
import { Item } from 'src/modules/root/models/item';
import { Zone } from 'src/modules/root/models/zone';

@Injectable({
  providedIn: 'root',
})
export class WaiterTablesService {
  constructor(private http: HttpClient) {}

  getAllZones(): Observable<Zone[]> {
    return this.http.get<Zone[]>(API_GET_ALL_ZONES);
  }

  getOrder(id: number): Observable<Order> {
    return this.http.get<Order>(API_GET_ORDER + id);
  }

  getTablesForZone(id: number): Observable<Table[]> {
    return this.http.get<Table[]>(API_GET_TABLES_FOR_ZONE + id);
  }

  getItemsForOrder(id: number): Observable<Item[]> {
    return this.http.get<Item[]>(API_GET_ITEMS_FOR_ORDER + id);
  }

  changeTableState(id: number, state: string, code: number): Observable<void> {
    return this.http
      .put<void>(API_CHANGE_TABLE_STATE + `${id}/${state}/${code}`, null)
      .pipe(catchError(this.errorHander));
  }

  changeItemState(id: number, state: string, code: number): Observable<void> {
    return this.http
      .put<Observable<void>>(API_CHANGE_ITEM_STATE + `${id}/${state}/${code}`, null)
      .pipe(catchError(this.errorHander));
  }

  addItemToOrder(id: number, code: number, item: Item): Observable<Item> {
    return this.http.put<Observable<Item>>(API_ADD_ITEM_TO_ORDER + `${id}/${code}`, item)
    .pipe(catchError(this.errorHander));
  }

  removeItemFromOrder(orderId: number, code: number, itemId: number): Observable<void>{
    return this.http.delete<Observable<void>>(API_REMOVE_ITEM_FROM_ORDER + `${orderId}/${itemId}/${code}`)
    .pipe(catchError(this.errorHander));
  }

  deleteOrder(id: number, code: number): Observable<void>{
    return this.http.delete<Observable<void>>(API_DELETE_ORDER + `${id}/${code}`)
    .pipe(catchError(this.errorHander));
  }

  errorHander(error: HttpErrorResponse): Observable<any> {
    return throwError(error);
  }
}
