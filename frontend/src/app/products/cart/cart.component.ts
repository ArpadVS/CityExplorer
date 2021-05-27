import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { CartItem } from 'src/app/models/cart.model';
import { ToastrService } from 'ngx-toastr';
// import { MessengerService } from 'src/app/services/messenger.service'
// import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  cartItems: CartItem[] = [
    {
      id: 1,
      name: 'Item1',
      quantity: 1,
      price: 400
    },
    {
      id: 2,
      name: 'Item2',
      quantity: 1,
      price: 300
    },
    {
      id: 3,
      name: 'Item1',
      quantity: 2,
      price: 55
    },
    {
      id: 4,
      name: 'Apple',
      quantity: 2,
      price: 2000
    }];

  cartTotal = 0;

  constructor(
    // private msg: MessengerService,
    // private cartService: CartService
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    // this.handleSubscription();
    this.loadCartItems();
  }
  /*
  handleSubscription() {
    this.msg.getMsg().subscribe((product: Product) => {
      this.loadCartItems();
    });
  }
  */

  loadCartItems() {
    // this.cartService.getCartItems().subscribe((items: CartItem[]) => {
    //   this.cartItems = items;
    //   this.calcCartTotal();
    // });
    this.calcCartTotal();
  }

  calcCartTotal() {
    this.cartTotal = 0;
    this.cartItems.forEach(item => {
      this.cartTotal += (item.quantity * item.price);
    });
  }

  checkout() {
    this.toastr.success('Succesful checkout!');
    localStorage.setItem('cart', JSON.stringify(this.cartItems));
    const asd: any = localStorage.getItem('cart');
    console.log(JSON.parse(asd));
  }
}
