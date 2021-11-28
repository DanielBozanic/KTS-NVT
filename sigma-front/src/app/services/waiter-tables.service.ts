import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  API_GET_ALL_ZONES,
  API_GET_TABLES_FOR_ZONE,
  API_GET_ITEMS_FOR_ORDER
} from '../api-routes';
import { Table } from '../models/table';
import { Item } from '../models/item';
import { Zone } from '../models/zone';

@Injectable({
  providedIn: 'root',
})
export class WaiterTablesService {
  constructor(private http: HttpClient) {}

  getAllZones(): Observable<Zone[]> {
    return this.http.get<Zone[]>(API_GET_ALL_ZONES);
  }

  getTablesForZone(id: number): Observable<Table[]> {
    return this.http.get<Table[]>(API_GET_TABLES_FOR_ZONE + id);
  }

  getItemsForOrder(id: number): Observable<Item[]> {
    return this.http.get<Item[]>(API_GET_ITEMS_FOR_ORDER + id)
  }

  errorHander(error: HttpErrorResponse): Observable<any> {
    return throwError(error);
  }
}