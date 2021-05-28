import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import { RegisterComponent } from './authentication/register/register.component';
import { AppComponent } from './app.component';
import { ProfileComponent } from './user/profile/profile.component';
import { CartComponent } from './products/cart/cart.component';
import { HomepageComponent } from './homepage/homepage.component';
import { RoleGuard } from './core/guards/role.guard';
import { ProductPageComponent } from './products/product-page/product-page.component';
import { ActivityDetailedComponent } from './activities/activity-detailed/activity-detailed.component'


const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full' },
  {path: 'home', component: HomepageComponent },
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {
    path: 'products',
    component: ProductPageComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_REGISTERED_USER|ROLE_ADMIN'}
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_REGISTERED_USER|ROLE_ADMIN'}
  },
  {path: 'activity/:id', component: ActivityDetailedComponent},
  { path: '**', component: HomepageComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
