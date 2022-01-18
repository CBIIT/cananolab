import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchSamplesByPublicationComponent } from './search-samples-by-publication.component';

describe('SearchSamplesByPublicationComponent', () => {
  let component: SearchSamplesByPublicationComponent;
  let fixture: ComponentFixture<SearchSamplesByPublicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchSamplesByPublicationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchSamplesByPublicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
