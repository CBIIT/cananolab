import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SampleAdvancedSearchComponent } from './sample-advanced-search.component';

describe('SampleAdvancedSearchComponent', () => {
  let component: SampleAdvancedSearchComponent;
  let fixture: ComponentFixture<SampleAdvancedSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SampleAdvancedSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SampleAdvancedSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
