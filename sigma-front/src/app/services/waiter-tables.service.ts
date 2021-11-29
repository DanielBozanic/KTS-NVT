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
  API_GET_ORDER
} from '../api-routes';
import { Table } from '../models/table';
import { Item } from '../models/item';
import { Zone } from '../models/zone';
import { Order } from '../models/order';

@Injectable({
  providedIn: 'root',
})
export class WaiterTablesService {
  constructor(private http: HttpClient) {}

  getAllZones(): Observable<Zone[]> {
    return this.http.get<Zone[]>(API_GET_ALL_ZONES);
  }

  getOrder(id : number): Observable<Order> {
    return this.http.get<Order>(API_GET_ORDER + id);
  }

  getTablesForZone(id: number): Observable<Table[]> {
    return this.http.get<Table[]>(API_GET_TABLES_FOR_ZONE + id);
  }

  getItemsForOrder(id: number): Observable<Item[]> {
    return this.http.get<Item[]>(API_GET_ITEMS_FOR_ORDER + id)
  }

  changeTableState(id : number, state : string, code : number) : Observable<void>{
    return this.http.put<void>(API_CHANGE_TABLE_STATE + `${id}/${state}/${code}`, null)
    .pipe(catchError(this.errorHander));
  }

  changeItemState(id : number, state : string, code : number) : void{
    this.http.put(API_CHANGE_ITEM_STATE + `${id}/${state}/${code}`, null)
    .pipe(catchError(this.errorHander));
  }

  errorHander(error: HttpErrorResponse): Observable<any> {
    return throwError(error);
  }
}