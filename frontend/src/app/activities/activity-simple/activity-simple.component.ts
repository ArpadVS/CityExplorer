import { Component, OnInit, Input } from '@angular/core';
import { Activity } from 'src/app/models/activity.model';
import { ActivityService } from 'src/app/core/services/activity.service'
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-activity-simple',
  templateUrl: './activity-simple.component.html',
  styleUrls: ['./activity-simple.component.scss']
})
export class ActivitySimpleComponent implements OnInit {
  @Input() activity: Activity;
  constructor(
    private toastr: ToastrService,
    private router : Router) { }

  ngOnInit() {
  }

  getDetails(id: number){
    let link: String = 'activity/' + id;
    this.router.navigate([link]);
  }

  goBack(){
    this.router.navigate(['/home']);
  }

}
