import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSatisfactionComponent } from './user-satisfaction.component';

describe('UserSatisfactionComponent', () => {
  let component: UserSatisfactionComponent;
  let fixture: ComponentFixture<UserSatisfactionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserSatisfactionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserSatisfactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
