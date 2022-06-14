import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchSamplesByPublicationResultsComponent } from './search-samples-by-publication-results.component';

describe('SearchSamplesByPublicationResultsComponent', () => {
  let component: SearchSamplesByPublicationResultsComponent;
  let fixture: ComponentFixture<SearchSamplesByPublicationResultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchSamplesByPublicationResultsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchSamplesByPublicationResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
