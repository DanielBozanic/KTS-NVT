import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import {
  API_GET_TABLE_FOR_ZONE_BY_CURRENT_PAGE,
  API_GET_NUMBER_OF_TABLES_FOR_ZONE_RECORDS,
  API_GET_ALL_ZONES,
} from 'src/modules/root/api-routes';
import { Table } from 'src/modules/root/models/table';
import { Zone } from 'src/modules/root/models/zone';

@Injectable({
  providedIn: 'root',
})
export class ZoneManagerService {
  constructor(private http: HttpClient) {}

  getTablesForZoneByCurrentPage(
    zoneId: number,
    currentPage: number,
    pageSize: number
  ): Observable<Array<Table>> {
    return this.http.get<Array<Table>>(
      `${API_GET_TABLE_FOR_ZONE_BY_CURRENT_PAGE}/${zoneId}?currentPage=${currentPage}&pageSize=${pageSize}`
    );
  }

  getNumberOfTablesForZoneRecords(zoneId: number): Observable<number> {
    return this.http.get<number>(
      `${API_GET_NUMBER_OF_TABLES_FOR_ZONE_RECORDS}/${zoneId}`
    );
  }

  getZones(): Observable<Array<Zone>> {
    return this.http.get<Array<Zone>>(`${API_GET_ALL_ZONES}`);
  }

  errorHander(error: HttpErrorResponse): Observable<any> {
    return throwError(error);
  }
}
