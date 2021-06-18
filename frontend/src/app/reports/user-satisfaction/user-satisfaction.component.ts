import { Component, OnInit } from '@angular/core';
import { UserSatisfaction } from 'src/app/models/satisfaction.model';
import { ReportService } from 'src/app/core/services/reports.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
@Component({
  selector: 'app-user-satisfaction',
  templateUrl: './user-satisfaction.component.html',
  styleUrls: ['./user-satisfaction.component.scss']
})
export class UserSatisfactionComponent implements OnInit {

  satisfiedUsers: UserSatisfaction[];
  dissatisfiedUsers: UserSatisfaction[];

  constructor(  
    private reportService: ReportService,
    private toastr: ToastrService,
    private router: Router
    ) { }

  ngOnInit() {
    this.getSatisfiedUsers();
    this.getDissatisfiedUsers();
  }
  
  getSatisfiedUsers() {
    this.reportService.getSatisfiedUsers().subscribe(
      (response => {
        if (response != null) {
          this.satisfiedUsers = response;
        }
      }),
      (error => {
        this.toastr.error("An error occured");
      })
    );
  }

  getDissatisfiedUsers() {
    this.reportService.getDissatisfiedUsers().subscribe(
      (response => {
        if (response != null) {
          this.dissatisfiedUsers = response;
        }
      }),
      (error => {
        this.toastr.error("An error occured");
      })
    );
  }

  goToDetails(id: number) {
    let link: String = 'activity/' + id;
    this.router.navigate([link]);
 }

}
