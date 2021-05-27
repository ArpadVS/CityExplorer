import { Component, OnInit, Input } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { CartItem } from 'src/app/models/cart.model';
import { ToastrService } from 'ngx-toastr';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-product-list-item',
  templateUrl: './product-list-item.component.html',
  styleUrls: ['./product-list-item.component.scss']
})
export class ProductListItemComponent implements OnInit {
  @Input() productItem: Product;
  inputValue: number;
  imageUrl = 'https://blog.ipleaders.in/wp-content/uploads/2020/02/814.jpg';
  

  form: FormGroup;

  constructor(
    private toast: ToastrService,
    private fb: FormBuilder,
  ) { }

  ngOnInit() {
    this.form = this.fb.group({
      quantity : [null, Validators.required]
    });
  }

  submit() {
    console.log(this.form.value.quantity);
    const quan = this.form.value.quantity;
    if (quan > this.productItem.quantity){
      this.toast.error('Maximum number you can order is ' + this.productItem.quantity);
      return;
    }}/*
    let cart: CartItem[] = JSON.parse(localStorage.getItem('cart'));
    let newCart: CartItem = {
      id: this.productItem.id,
      name: this.productItem.name,
      quantity: this.inputValue,
      price: this.productItem.id};
    if (cart.length === 0) {
      this.productItem.quantity = this.inputValue;
      cart.push(this.productItem);
      this.toast.success(JSON.stringify(this.productItem));
      console.log(JSON.stringify(this.productItem));
      return;
    }

  }

  onKey(event) {
    this.inputValue = event.target.value;
    console.log('Input now ' + this.inputValue);
  }*/


}
