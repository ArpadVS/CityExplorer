import { Injectable } from '@angular/core';

import { Product } from '../../models/product.model';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  readonly authenticationPath = 'http://localhost:8080/api/product';

  constructor(
    private http: HttpClient
  ) { }

  readonly productPath = 'http://localhost:8080/api/product';
  db: Product[] = [
    {
    id: 1,
    name: 'MacBook',
    price: 2000,
    quantity: 5,
    category: 'Laptops',
    bonusPercentage: 0
    },
    {
    id: 2,
    name: 'Apple',
    price: 30,
    quantity: 500,
    category: 'Food',
    bonusPercentage: 0
    },
    {
    id: 3,
    name: 'GAMING CHAIR',
    price: 750,
    quantity: 8,
    category: 'Furniture',
    bonusPercentage: 0
    },
    {
    id: 4,
    name: 'Cheetos',
    price: 80,
    quantity: 50,
    category: 'FOOD',
    bonusPercentage: 0
    },
  ];

  getAll(): Product[] {
    return this.db;
  }

  /*
  add(newAddress: Address): Observable<any> {
    return this.http.post('http://localhost:8080/api/address', newAddress, {headers: this.headers, responseType: 'json'});
  }

  get(addressId: string): Observable<any> {
    return this.http.get('http://localhost:8080/api/address/'.concat(addressId), {headers: this.headers, responseType: 'json'});
  }

  getAll(): Observable<any> {
    return this.http.get('http://localhost:8080/api/address', {headers: this.headers, responseType: 'json'});
  }
  update(updAddress: Address, addressId: string): Observable<any> {
    // const item = localStorage.getItem('user');
    // const decodedItem = JSON.parse(item);
    return this.http.put('http://localhost:8080/api/address/'.concat(addressId), updAddress, {headers: this.headers, responseType: 'json'});
  }

  delete(addressId: string): Observable<any> {
    return this.http.delete('http://localhost:8080/api/address/'.concat(addressId), {headers: this.headers, responseType: 'text'});
  }*/
}
