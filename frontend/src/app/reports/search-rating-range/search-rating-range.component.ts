import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Activity } from 'src/app/models/activity.model';
import { ReportService } from 'src/app/core/services/reports.service';
import { RatingRangeDTO } from 'src/app/models/rating-range-dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-rating-range',
  templateUrl: './search-rating-range.component.html',
  styleUrls: ['./search-rating-range.component.scss']
})
export class SearchRatingRangeComponent implements OnInit {

  private minRating: number;
  private maxRating: number;
  private showResult: boolean;
  private activities: Activity[];


  constructor( 
    private reportService: ReportService,
    private toastr: ToastrService,
    private router: Router ) { }

  ngOnInit() {
    this.minRating = 0;
    this.maxRating = 5;
    this.showResult = false;
  }

  getActivities() {
    if(this.minRating < 0 || this.minRating > 5 ) {
      this.toastr.warning("Ratings must be between 0 and 5!");
      return;
    }
    if(this.maxRating < 0 || this.maxRating > 5 ) {
      this.toastr.warning("Ratings must be between 0 and 5!");
      return;
    }
    
    if(this.minRating > this.maxRating ) {
      this.toastr.warning("First number must be smaller then second!");
      return;
    }
    const dto = new  RatingRangeDTO(this.minRating, this.maxRating);
    this.reportService.getActivitiesByRatingRange(dto).subscribe(
      (response => {
        if (response !== null) {
          console.log(response);
          this.activities = response;
          this.showResult = true;
        }
      }),
      (error => {
        this.toastr.error("An error occured");
      })
    );
  }

  getDetails(id: number){
    let link: String = 'activity/' + id;
    this.router.navigate([link]);
  }

}
