import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchPublicationComponent } from './search-publication.component';

describe('SearchPublicationComponent', () => {
  let component: SearchPublicationComponent;
  let fixture: ComponentFixture<SearchPublicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchPublicationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchPublicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
