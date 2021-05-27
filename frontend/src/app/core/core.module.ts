import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AuthenticationService } from './services/authentication.service';
import { MaterialModule } from '../material/material.module';
import { NavbarUserComponent } from './components/navbar-user/navbar-user.component';
import { ConstantsService } from './services/constants.service';
import { NavbarRegisteredUserComponent } from './components/navbar-registered-user/navbar-registered-user.component';
import { NavbarAdminComponent } from './components/navbar-admin/navbar-admin.component';



@NgModule({
  declarations: [NavbarUserComponent, NavbarRegisteredUserComponent, NavbarAdminComponent],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule
  ],
  providers: [
    AuthenticationService,
    ConstantsService
  ],
  exports: [
     NavbarUserComponent, NavbarAdminComponent, NavbarRegisteredUserComponent
  ]

})
export class CoreModule {
  constructor( @Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule has already been loaded. You should only import Core modules in the AppModule only.');
    }
  }
}
