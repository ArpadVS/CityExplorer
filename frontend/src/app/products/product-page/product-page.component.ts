import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.scss']
})
export class ProductPageComponent implements OnInit {
  productList: Product[] = [
    {
      id: 1,
      name: 'Activity1',
      price: 200,
      quantity: 20,
      category: 'aasdfdsfsd',
      bonusPercentage: 15
    },{
      id: 2,
      name: 'Carting at nSseme',
      price: 2000,
      quantity: 20,
      category: 'aasdfdsfsd',
      bonusPercentage: 15
    },{
      id: 3,
      name: 'Activity1',
      price: 2050,
      quantity: 20,
      category: 'asdasd',
      bonusPercentage: 15
    },{
      id: 4,
      name: 'addfsdf',
      price: 200,
      quantity: 20,
      category: 'aasdfdsfsd',
      bonusPercentage: 15
    },
  ];

  constructor(
  ) { }

  ngOnInit() {
  }

}
