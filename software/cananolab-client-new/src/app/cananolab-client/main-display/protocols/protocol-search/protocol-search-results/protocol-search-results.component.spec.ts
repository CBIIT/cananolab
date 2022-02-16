import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtocolSearchResultsComponent } from './protocol-search-results.component';

describe('ProtocolSearchResultsComponent', () => {
  let component: ProtocolSearchResultsComponent;
  let fixture: ComponentFixture<ProtocolSearchResultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProtocolSearchResultsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProtocolSearchResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
