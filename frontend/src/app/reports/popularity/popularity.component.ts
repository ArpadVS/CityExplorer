import { Component, OnInit } from '@angular/core';
import { ReportService } from 'src/app/core/services/reports.service';
import { Activity } from 'src/app/models/activity.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-popularity',
  templateUrl: './popularity.component.html',
  styleUrls: ['./popularity.component.scss']
})
export class PopularityComponent implements OnInit {

  private mostRecommended: Activity;
  private leastRecommended: Activity;

  constructor( 
    private reportService: ReportService,
    private toastr: ToastrService
     ){ }

  ngOnInit() {
    this.getPopularityReport();
  }

  getPopularityReport() {
    this.reportService.getPopularityReport().subscribe(
      (response => {
        if (response != null) {
          this.mostRecommended = response.mostRecommended;
          this.leastRecommended = response.leastRecommended;
        }
      }),
      (error => {
        this.toastr.error("An error occured");
      })
    );
  }


}
