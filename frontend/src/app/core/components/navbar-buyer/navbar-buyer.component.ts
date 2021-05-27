import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { JwtHelperService } from '@auth0/angular-jwt';
@Component({
  selector: 'app-navbar-buyer',
  templateUrl: './navbar-buyer.component.html',
  styleUrls: ['./navbar-buyer.component.scss']
})
export class NavbarBuyerComponent implements OnInit {

  constructor(private router: Router, private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  logOut(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('cart');
    this.toastr.success('Succesful logout!');
    this.router.navigate(['']);
  }

}
