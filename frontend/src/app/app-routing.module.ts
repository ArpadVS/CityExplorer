import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import { RegisterComponent } from './authentication/register/register.component';
import { ProfileComponent } from './user/profile/profile.component';
import { HomepageComponent } from './homepage/homepage.component';
import { RoleGuard } from './core/guards/role.guard';
import { RecommendationComponent } from './recommendation/recommendation.component';
import { ActivityDetailedComponent } from './activities/activity-detailed/activity-detailed.component'
import { ReportsPageComponent } from './reports/reports-page/reports-page.component';
import { ActivityNewComponent } from "./activities/activity-new/activity-new.component";
import { AllUsersComponent } from './reports/all-users/all-users.component';
 

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full' },
  {path: 'home', component: HomepageComponent },
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {
    path: 'recommend',
    component: RecommendationComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_REGISTERED_USER'}
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_REGISTERED_USER|ROLE_ADMIN'}
  },
  {
    path: 'reports',
    component: ReportsPageComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_ADMIN'}
  },  
  {
    path: 'users',
    component: AllUsersComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_ADMIN'}
  },
  {
    path: 'newActivity',
    component: ActivityNewComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_ADMIN'}
  },
  {path: 'activity/:id', component: ActivityDetailedComponent},
  { path: '**', component: HomepageComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
