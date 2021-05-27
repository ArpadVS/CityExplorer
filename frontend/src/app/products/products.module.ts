import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartComponent } from './cart/cart.component';
import { CartItemComponent } from './cart/cart-item/cart-item.component';

import { AppRoutingModule } from 'src/app/app-routing.module';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { ProductPageComponent } from './product-page/product-page.component';
import { ProductFilterComponent } from './product-filter/product-filter.component';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductListItemComponent } from './product-list/product-list-item/product-list-item.component';



@NgModule({
  declarations: [CartComponent, CartItemComponent, ProductPageComponent, ProductFilterComponent, ProductListComponent, ProductListItemComponent],
  imports: [
    CommonModule,
    AppRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [
    CartComponent,
    CartItemComponent,
    ProductPageComponent,
    ProductFilterComponent,
    ProductListComponent
  ]
})
export class ProductsModule { }
