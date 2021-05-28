import { error } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ActivityService } from '../../core/services/activity.service';
import { Activity } from '../../models/activity.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-activity-detailed',
  templateUrl: './activity-detailed.component.html',
  styleUrls: ['./activity-detailed.component.scss']
})
export class ActivityDetailedComponent implements OnInit {
  activityId: number;
  activity : Activity;
  private routeSub: any;

  constructor(
    private route: ActivatedRoute,
    private activityService: ActivityService,
    private toastr : ToastrService,
    private router : Router
  ) { }

  ngOnInit() {
    this.routeSub = this.route.params.subscribe(params => {
      console.log(params) //log the entire params object
      console.log(params['id']) //log the value of id
      this.activityId = params['id'];
    });
    this.getActivity();
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }

  getActivity(){
    this.activityService.getOne(this.activityId).subscribe(
      (response => {
        console.log(response);
        this.activity = response;
        console.log('Retrieved data.');
      }),
      (error => {
        this.router.navigate(['home']);
        this.toastr.error('Activity doesn\'t exist!');
      })
    );
  }
 

}
