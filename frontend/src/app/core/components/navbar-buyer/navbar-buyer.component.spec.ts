import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarBuyerComponent } from './navbar-buyer.component';

describe('NavbarBuyerComponent', () => {
  let component: NavbarBuyerComponent;
  let fixture: ComponentFixture<NavbarBuyerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavbarBuyerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarBuyerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
