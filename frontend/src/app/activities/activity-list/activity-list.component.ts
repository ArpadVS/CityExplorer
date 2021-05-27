import { Component, OnInit } from '@angular/core';
import { Activity } from 'src/app/models/activity.model';
import { ActivityService } from 'src/app/core/services/activity.service'
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-activity-list',
  templateUrl: './activity-list.component.html',
  styleUrls: ['./activity-list.component.scss']
})
export class ActivityListComponent implements OnInit {
  private activities: Activity[];

  constructor(
    private activityService: ActivityService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.getActivities();
  }

  getActivities() {
    this.activityService.getAll().subscribe(
      (response => {
        console.log(response);
        if (response != null) {
          this.activities = response;
          console.log("Retrieved data");
        }
      }),
      (error => {
        this.toastr.error('Cannot retrieve activities!');
      })
    );
  }

  getDetails(id: number){
    this.toastr.info('Clicked on id ' + id);
  }

}
