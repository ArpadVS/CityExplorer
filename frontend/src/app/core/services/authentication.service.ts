import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ConstantsService } from './constants.service';
import { Registration } from 'src/app/models/registration.model';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  readonly authenticationPath = 'http://localhost:8080/api/auth';
  constructor(
    private http: HttpClient
  ) { }

  login(auth: any): Observable<any> {
    return this.http.post(this.authenticationPath + '/login',
    {username: auth.username, password: auth.password}, {headers: this.headers, responseType: 'text'});
  }

  logout(): Observable<any> {
    return this.http.get(this.authenticationPath + '/logout', {headers: this.headers, responseType: 'text'});
  }

  register(registration: Registration): Observable<any> {
    return this.http.post(this.authenticationPath + '/registration', registration,
      {headers: this.headers, responseType: 'json'});
  }

  isLoggedIn(): boolean {
    if (localStorage.getItem('user')) {
        return true;
    }
    return false;
  }

  getToken(): string {
    return localStorage.getItem('user');
  }

  whoAmI(): Observable<any> {
    console.log(localStorage.getItem('user'));
    return this.http.get( this.authenticationPath + '/whoami', {headers: this.headers, responseType: 'json'});
  }

  getRole(): string {
    const token = this.getToken();
    console.log(token);
    const jwt: JwtHelperService = new JwtHelperService();

    if (!token) {
      return 'NO_ROLE';
    }
    console.log(jwt.decodeToken(token).role);
    return jwt.decodeToken(token).role;
  }

}
