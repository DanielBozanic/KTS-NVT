import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {
  API_GET_TABLES_FOR_ZONE,
  API_GET_ALL_ZONES,
  API_CREATE_NEW_ZONE,
  API_ADD_TABLE,
  API_UPDATE_NUMBER_OF_CHAIRS,
  API_REMOVE_TABLE_FROM_ZONE,
  API_UPDATE_TABLE_POSITION,
} from 'src/modules/root/api-routes';
import { Table } from 'src/modules/root/models/table';
import { Zone } from 'src/modules/root/models/zone';

@Injectable({
  providedIn: 'root',
})
export class ZoneManagerService {
  constructor(private http: HttpClient) {}

  getTablesForZone(zoneId: number): Observable<Array<Table>> {
    return this.http.get<Array<Table>>(`${API_GET_TABLES_FOR_ZONE}${zoneId}`);
  }

  getZones(): Observable<Array<Zone>> {
    return this.http.get<Array<Zone>>(`${API_GET_ALL_ZONES}`);
  }

  createNewZone(zone: Zone): Observable<Zone> {
    return this.http
      .post<Zone>(API_CREATE_NEW_ZONE, zone)
      .pipe(catchError(this.errorHander));
  }

  addTable(table: Table): Observable<Table> {
    return this.http
      .post<Table>(API_ADD_TABLE, table)
      .pipe(catchError(this.errorHander));
  }

  updateNumberOfChairs(table: Table): Observable<Table> {
    return this.http
      .put<Table>(API_UPDATE_NUMBER_OF_CHAIRS, table)
      .pipe(catchError(this.errorHander));
  }

  updateTablePosition(table: Table): Observable<Table> {
    return this.http
      .put<Table>(API_UPDATE_TABLE_POSITION, table)
      .pipe(catchError(this.errorHander));
  }

  removeTableFromZone(table: Table): Observable<Array<Table>> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: table,
    };
    return this.http
      .delete<Array<Table>>(API_REMOVE_TABLE_FROM_ZONE, options)
      .pipe(catchError(this.errorHander));
  }

  errorHander(error: HttpErrorResponse): Observable<any> {
    return throwError(error);
  }
}
