import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, CanActivate } from '@angular/router';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(
   public auth: AuthenticationService,
   public router: Router
  ) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRoles: string = route.data.expectedRoles;
    const token = localStorage.getItem('user');
    const jwt: JwtHelperService = new JwtHelperService();

    if (!token) {
     this.router.navigate(['/login']);
     return false;
    }

    const info = jwt.decodeToken(token);
    const roles: string[] = expectedRoles.split('|');
    const role11: string = info.role[0].authority;
    let retVal = false;
    roles.forEach(role => {
      if (role === role11) {
        retVal = true;
        return;
      }
    });
    console.log('done');
    return retVal;

}
}
