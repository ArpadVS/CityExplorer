import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AuthenticationService } from './services/authentication.service';
import { MaterialModule } from '../material/material.module';
import { NavbarUserComponent } from './components/navbar-user/navbar-user.component';
import { ConstantsService } from './services/constants.service';
import { NavbarSellerComponent } from './components/navbar-seller/navbar-seller.component';
import { NavbarBuyerComponent } from './components/navbar-buyer/navbar-buyer.component';
import { NavbarManagerComponent } from './components/navbar-manager/navbar-manager.component';



@NgModule({
  declarations: [NavbarUserComponent, NavbarSellerComponent, NavbarBuyerComponent, NavbarManagerComponent],
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
     NavbarUserComponent, NavbarSellerComponent, NavbarBuyerComponent, NavbarManagerComponent
  ]

})
export class CoreModule {
  constructor( @Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule has already been loaded. You should only import Core modules in the AppModule only.');
    }
  }
}
