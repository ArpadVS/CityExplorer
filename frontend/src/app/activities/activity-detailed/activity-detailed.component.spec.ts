import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityDetailedComponent } from './activity-detailed.component';

describe('ActivityDetailedComponent', () => {
  let component: ActivityDetailedComponent;
  let fixture: ComponentFixture<ActivityDetailedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivityDetailedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityDetailedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
