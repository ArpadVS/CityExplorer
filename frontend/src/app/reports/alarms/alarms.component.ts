import { Component, OnInit } from '@angular/core';
import { PageEvent, MatTableDataSource} from '@angular/material';
import { ReportService } from 'src/app/core/services/reports.service';
import { ToastrService } from 'ngx-toastr';
import { Activity } from 'src/app/models/activity.model'

@Component({
  selector: 'app-alarms',
  templateUrl: './alarms.component.html',
  styleUrls: ['./alarms.component.scss']
})
export class AlarmsComponent implements OnInit {

  alarms = new MatTableDataSource<Activity>();
  displayedColumns: string[] = [ 'name', 'average', 'creationDate', 'numOfRatings'];

  constructor(
    private reportService : ReportService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.getAlarmedActivities();
  }

  getAlarmedActivities() {
    this.reportService.getAlarms().subscribe(
      (response => {
        if (response !== null) {
          this.alarms.data = response;
        }
      }),
      (error => {
        this.toastr.error("An error occured!");
      })
    );
  }

  getDetailed(id: number ) {
    window.open('/activity/' + id);
  }

  
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.alarms.filter = filterValue.trim().toLowerCase();
  }

}
