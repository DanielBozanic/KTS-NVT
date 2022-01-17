import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Response } from './helpers/response';

const AUTH_API = 'http://localhost:8080/authenticate/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<Response> {
    return this.http.post<Response>(
      AUTH_API,
      { username, password },
      httpOptions
    );
  }
}
