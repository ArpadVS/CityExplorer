import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivitySimpleComponent } from './activity-simple.component';

describe('ActivitySimpleComponent', () => {
  let component: ActivitySimpleComponent;
  let fixture: ComponentFixture<ActivitySimpleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivitySimpleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivitySimpleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
