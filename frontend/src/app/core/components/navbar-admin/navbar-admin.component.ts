import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-navbar-admin',
  templateUrl: './navbar-admin.component.html',
  styleUrls: ['./navbar-admin.component.scss']
})
export class NavbarAdminComponent implements OnInit {

  constructor(private router: Router, private toastr: ToastrService) { }

  ngOnInit() {
  }

  logOut(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('cart');
    this.toastr.success('Succesful logout!');
    this.router.navigate(['/home']);
  }

  
  goHome(){
    this.router.navigate(['/home']);
  }

}
