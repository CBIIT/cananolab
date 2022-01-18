import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtocolSearchComponent } from './protocol-search.component';

describe('ProtocolSearchComponent', () => {
  let component: ProtocolSearchComponent;
  let fixture: ComponentFixture<ProtocolSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProtocolSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProtocolSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
