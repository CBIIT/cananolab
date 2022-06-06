import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvancedSearchResultsComponent } from './advanced-search-results.component';

describe('AdvancedSearchResultsComponent', () => {
  let component: AdvancedSearchResultsComponent;
  let fixture: ComponentFixture<AdvancedSearchResultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdvancedSearchResultsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvancedSearchResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
