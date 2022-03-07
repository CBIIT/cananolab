import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SampleSearchResultsComponent } from './sample-search-results.component';

describe('SampleSearchResultsComponent', () => {
  let component: SampleSearchResultsComponent;
  let fixture: ComponentFixture<SampleSearchResultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SampleSearchResultsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SampleSearchResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
