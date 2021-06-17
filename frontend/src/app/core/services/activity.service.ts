import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Activity } from 'src/app/models/activity.model';
import { UserRequirementsDTO } from 'src/app/models/requirements.model';
import { Rating } from 'src/app/models/rating.model';

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

  
  search(searchTerm: any): Observable<Activity[]> {
    return this.http.post<Activity[]>(this.activityPath + "/search", searchTerm ,{headers: this.headers, responseType: 'json'});
  }

  getOne(id :number ): Observable<Activity> {
    return this.http.get<Activity>(this.activityPath + '/details/' + id, {headers: this.headers, responseType: 'json'});
  }

  getRecommendation(requirements: UserRequirementsDTO): Observable<any> {
    return this.http.post(this.activityPath + '/getRecommendation', requirements,
      {headers: this.headers, responseType: 'json'});
  }

  newActivity(activity: Activity): Observable<any> {
    return this.http.post(this.activityPath + '/new', activity,
      {headers: this.headers, responseType: 'json'});
  }
  
  rateActivity(rating: Rating): Observable<any> {
    return this.http.post(this.activityPath + '/rate', rating,
      {headers: this.headers, responseType: 'json'});
  }
  
}
