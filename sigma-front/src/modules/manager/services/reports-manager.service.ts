import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Report } from 'src/modules/root/models/report';
import { API_POST_REPORT } from 'src/modules/root/api-routes';

@Injectable({
  providedIn: 'root',
})
export class ReportsManagerService {
  constructor(private http: HttpClient) {}

  postReportQuery(report: Report): Observable<Report> {
    return this.http
      .post<Report>(API_POST_REPORT, report)
      .pipe(catchError(this.errorHander));
  }

  errorHander(error: HttpErrorResponse): Observable<any> {
    return throwError(error);
  }
}
