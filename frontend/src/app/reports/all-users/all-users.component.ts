import { Component, OnInit, ViewChild} from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { Registration } from 'src/app/models/registration.model';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.scss']
})
export class AllUsersComponent implements OnInit {

  users = new MatTableDataSource<Registration>();
  displayedColumns: string[] = [ 'name', 'username', 'status', 'block'];
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor( 
    private userService: UserService, 
    private toastr: ToastrService) { }

  ngOnInit() {
    this.users.paginator = this.paginator;
    this.getUsers();
  }

  getUsers() {
    this.userService.getUsers(this.users.paginator).subscribe(
      (response => {
        if (response !== null) {
          this.users.data = response;
          if (this.users.data.length > 0) {
            this.users.paginator.length = this.users.data[0].size;
          }
        }
      }),
      (error => {
        this.toastr.error("An error occured");
      })
    );
  }

  deactivateUser( username: string) {
    this.userService.deactivateUser(username).subscribe(
      (response => {
        if (response === true) {
          this.getUsers();
          this.toastr.success('User blocked!');
        }
      }),
      (error => {
        this.toastr.error("An error occured");
      })
    );
  }

  
  activateUser( username: string) {
    this.userService.activateUser(username).subscribe(
      (response => {
        if (response === true) {
          this.getUsers();
          this.toastr.success('User unblocked!');
        }
      }),
      (error => {
        this.toastr.error("An error occured");
      })
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.users.filter = filterValue.trim().toLowerCase();
    if (this.users.paginator) {
      this.users.paginator.firstPage();
    }
  }


}
