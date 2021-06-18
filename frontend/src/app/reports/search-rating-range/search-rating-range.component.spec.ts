import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchRatingRangeComponent } from './search-rating-range.component';

describe('SearchRatingRangeComponent', () => {
  let component: SearchRatingRangeComponent;
  let fixture: ComponentFixture<SearchRatingRangeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchRatingRangeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchRatingRangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
