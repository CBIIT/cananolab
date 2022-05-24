import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchpublicationresultsComponent } from './searchpublicationresults.component';

describe('SearchpublicationresultsComponent', () => {
  let component: SearchpublicationresultsComponent;
  let fixture: ComponentFixture<SearchpublicationresultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchpublicationresultsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchpublicationresultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
