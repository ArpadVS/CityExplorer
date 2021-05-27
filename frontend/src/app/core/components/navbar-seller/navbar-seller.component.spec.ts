import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarSellerComponent } from './navbar-seller.component';

describe('NavbarSellerComponent', () => {
  let component: NavbarSellerComponent;
  let fixture: ComponentFixture<NavbarSellerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavbarSellerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarSellerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
