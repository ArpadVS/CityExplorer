import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ConstantsService } from './constants.service';
import { Observable } from 'rxjs';
import { Activity } from 'src/app/models/activity.model';
import { PageEvent } from '@angular/material';
import { PopularityDto } from 'src/app/models/popularity.model';
import { UserSatisfaction } from 'src/app/models/satisfaction.model';
import { RatingRangeDTO } from 'src/app/models/rating-range-dto'

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  readonly path = 'http://localhost:8080/api/reports';

  constructor(private http: HttpClient, private constants: ConstantsService) { }


  getActivitiesByRatingRange(dto: RatingRangeDTO): Observable<Activity[]> {
    return this.http.post<Activity[]>(this.path + '/getByRatingRange', dto, { headers: this.headers });
  }

  getPopularityReport(numOfMonths: number): Observable<PopularityDto> {
    return this.http.get<PopularityDto>(this.path + '/popularityReport/' + numOfMonths, { headers: this.headers });
  }

  getDissatisfiedUsers(): Observable<UserSatisfaction[]> {
    return this.http.get<UserSatisfaction[]>(this.path + '/getDissatisfiedUsers', { headers: this.headers });
  }
  
  getSatisfiedUsers(): Observable<UserSatisfaction[]> {
    return this.http.get<UserSatisfaction[]>(this.path + '/getSatisfiedUsers', { headers: this.headers });
  }

  getAlarms(): Observable<Activity[]> {
    return this.http.get<Activity[]>(this.path + '/getAlarms', {headers: this.headers});
  }

}
