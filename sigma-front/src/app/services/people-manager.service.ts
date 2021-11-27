import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from '../models/Employee';
import {
  API_GET_ALL_EMPLOYEES,
  API_ADD_EMPLOYEE,
  API_EDIT_EMPLOYEE,
  API_DELETE_EMPLOYEE,
} from '../api-routes';

@Injectable({
  providedIn: 'root',
})
export class PeopleManagerService {
  constructor(private http: HttpClient) {}

  getAllEmployees(): Observable<Array<Employee>> {
    return this.http.get<Array<Employee>>(API_GET_ALL_EMPLOYEES);
  }

  addEmployee(employee: Employee): Observable<Employee> {
    return this.http
      .post<Employee>(API_ADD_EMPLOYEE, employee)
      .pipe(catchError(this.errorHander));
  }

  editEmployee(employee: Employee): Observable<Employee> {
    return this.http
      .put<Employee>(API_EDIT_EMPLOYEE, employee)
      .pipe(catchError(this.errorHander));
  }

  deleteEmployee(employeeId: number): Observable<any> {
    return this.http
      .delete<Employee>(`${API_DELETE_EMPLOYEE}/${employeeId}`)
      .pipe(catchError(this.errorHander));
  }

  errorHander(error: HttpErrorResponse): Observable<any> {
    return throwError(error);
  }
}
