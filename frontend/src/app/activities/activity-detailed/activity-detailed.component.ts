import { error } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ActivityService } from '../../core/services/activity.service';
import { Activity } from '../../models/activity.model';
import { Report } from '../../models/reports.model'
import { ToastrService } from 'ngx-toastr';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { Rating } from 'src/app/models/rating.model';
import { JwtHelperService } from '@auth0/angular-jwt';
//import { Color, Label } from 'ng2-charts';

@Component({
  selector: 'app-activity-detailed',
  templateUrl: './activity-detailed.component.html',
  styleUrls: ['./activity-detailed.component.scss']
})
export class ActivityDetailedComponent implements OnInit {
  activityId: number;
  activity : Activity;
  role: string;
  private routeSub: any;

  // rating
  stars: number[] = [1, 2, 3, 4, 5];
  selectedValue: number;
  // charts
  barChartOptions: ChartOptions = { responsive: true};
  barChartLabels: Label[];
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [];
  barChartData: ChartDataSets[];

  constructor(
    private route: ActivatedRoute,
    private activityService: ActivityService,
    private toastr : ToastrService,
    private router : Router,
  ) {}

  ngOnInit() {
    this.routeSub = this.route.params.subscribe(params => {
      this.activityId = params['id'];
    });
    this.checkRole();
    this.getActivity();
  }

  checkRole() {
    const item = localStorage.getItem('user');

    if (!item) {
      this.role = undefined;
      return;
    }
    const jwt: JwtHelperService = new JwtHelperService();
    this.role = jwt.decodeToken(item).role[0].authority;
    console.log(this.role);
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }

  getActivity(){
    this.activityService.getOne(this.activityId).subscribe(
      (response => {
        console.log(response);
        this.activity = response;
        this.createChart();
        console.log("Average rating is " + this.activity.report.average);
        this.selectedValue = this.activity.report.myRating;
        console.log('Retrieved data.');
      }),
      (error => {
        this.router.navigate(['home']);
        this.toastr.error('Activity doesn\'t exist!');
      })
    );
  }

  createChart() {
    this.barChartLabels = ['1', '2', '3', '4', '5'];
    const report: Report = this.activity.report;
    this.barChartData = [
      { data: [report.ones, report.twos, report.threes, report.fours, report.fives], label: 'Grades' }
    ];
  }

  rateActivity() {
    const dto: Rating = new Rating();
    dto.rating = this.selectedValue;
    dto.activityId = this.activityId;
    this.activityService.rateActivity(dto).subscribe(
      (response => {
        if (response === true) {
          this.toastr.success('Rating succesful!');
          window.location.reload();
        } else {
          this.toastr.warning('You can only rate activities that were recommended to you.');
        }
      }),
      (error => {
        this.toastr.error("An error occured");
      })
    );
  }

}
