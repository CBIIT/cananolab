import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtocolEditProtocolAccessComponent } from './protocol-edit-protocol-access.component';

describe('ProtocolEditProtocolAccessComponent', () => {
  let component: ProtocolEditProtocolAccessComponent;
  let fixture: ComponentFixture<ProtocolEditProtocolAccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProtocolEditProtocolAccessComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProtocolEditProtocolAccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
