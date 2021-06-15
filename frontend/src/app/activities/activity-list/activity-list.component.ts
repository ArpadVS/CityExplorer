import { Component, OnInit } from '@angular/core';
import { Activity } from 'src/app/models/activity.model';
import { ActivityService } from 'src/app/core/services/activity.service'
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-activity-list',
  templateUrl: './activity-list.component.html',
  styleUrls: ['./activity-list.component.scss']
})
export class ActivityListComponent implements OnInit {
  private activities: Activity[];
  private searchInput: string = '';

  constructor(
    private activityService: ActivityService,
    private toastr: ToastrService,
    private router : Router
  ) { }

  ngOnInit() {
    this.getActivities();
  }

  getActivities() {
    this.activityService.getAll().subscribe(
      (response => {
        //console.log(response);
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
    let link: String = 'activity/' + id;
    this.router.navigate([link]);
  }

  search() {
    const searchTerm : any = {'name' : this.searchInput}

    this.activityService.search(searchTerm).subscribe(
      (response => {
        if (response != null) {   
          this.activities = response;  
          if(this.activities.length == 0){
            this.toastr.warning("No activities found for given term!");
          }     
        }
      }),
      (error => {
        this.toastr.error("Something went wrong!");
        this.getActivities();
      })
    );
  }

}
