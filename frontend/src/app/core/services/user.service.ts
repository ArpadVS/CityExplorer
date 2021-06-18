import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageEvent } from '@angular/material';
import { Registration } from 'src/app/models/registration.model';


@Injectable({
  providedIn: 'root'
})
export class UserService {


  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  readonly path = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  getUsers(event: PageEvent): Observable<Registration[]> {
    return this.http.get<Registration[]>(this.path  + '/?page=' + event.pageIndex + '&size=' +
    event.pageSize, {headers: this.headers});
  }

  deactivateUser(username: string): Observable<boolean> {
    return this.http.get<boolean>(this.path  + '/deactivateUser/' + username , {headers: this.headers});
  }
  
  activateUser(username: string): Observable<boolean> {
    return this.http.get<boolean>(this.path  + '/activateUser/' + username , {headers: this.headers});
  }
}
