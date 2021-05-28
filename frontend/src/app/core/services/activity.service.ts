import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Activity } from 'src/app/models/activity.model';

@Injectable({
  providedIn: 'root'
})
export class ActivityService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  readonly activityPath = 'http://localhost:8080/api/activities';
  constructor(
    private http: HttpClient
  ) { }

  getAll(): Observable<Activity[]> {
    return this.http.get<Activity[]>(this.activityPath, {headers: this.headers, responseType: 'json'});
  }

  getOne(id :number ): Observable<Activity> {
    return this.http.get<Activity>(this.activityPath + '/details/' + id, {headers: this.headers, responseType: 'json'});
  }
  
}
