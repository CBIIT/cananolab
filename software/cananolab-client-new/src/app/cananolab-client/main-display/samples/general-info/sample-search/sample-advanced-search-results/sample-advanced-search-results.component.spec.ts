import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SampleAdvancedSearchResultsComponent } from './sample-advanced-search-results.component';

describe('SampleAdvancedSearchResultsComponent', () => {
  let component: SampleAdvancedSearchResultsComponent;
  let fixture: ComponentFixture<SampleAdvancedSearchResultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SampleAdvancedSearchResultsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SampleAdvancedSearchResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
