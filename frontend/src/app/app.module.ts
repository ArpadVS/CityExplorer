import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { MaterialModule } from './material/material.module';
import { AuthenticationModule } from './authentication/authentication.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CoreModule } from './core/core.module';
import { JwtInterceptor } from './core/interceptors/jwt-interceptor.interceptor';
import { UserModule } from './user/user.module';
import { BootstrapModule } from './material/bootstrap/bootstrap.module';
import { TooltipModule } from 'ngx-bootstrap';
import { HomepageComponent } from './homepage/homepage.component';
import { ActivityListComponent } from './activities/activity-list/activity-list.component';
import { ActivityDetailedComponent } from './activities/activity-detailed/activity-detailed.component';
import { RecommendationComponent } from './recommendation/recommendation.component';
import { ActivitySimpleComponent } from './activities/activity-simple/activity-simple.component';
import { ChartsModule, ThemeService  } from 'ng2-charts';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReportsPageComponent } from './reports/reports-page/reports-page.component';
import { UserSatisfactionComponent } from './reports/user-satisfaction/user-satisfaction.component';
import { PopularityComponent } from './reports/popularity/popularity.component';
import { AlarmsComponent } from './reports/alarms/alarms.component';
import { ActivityNewComponent } from './activities/activity-new/activity-new.component';


@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    ActivityListComponent,
    ActivityDetailedComponent,
    RecommendationComponent,
    ActivitySimpleComponent,
    ReportsPageComponent,
    UserSatisfactionComponent,
    PopularityComponent,
    AlarmsComponent,
    ActivityNewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    BootstrapModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToastrModule.forRoot({
      progressBar: true,
      timeOut: 3500,
      closeButton: true,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true
    }),
    AuthenticationModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CoreModule,
    UserModule,
    TooltipModule.forRoot(),
    ChartsModule,
    NgbModule

  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}, ThemeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
