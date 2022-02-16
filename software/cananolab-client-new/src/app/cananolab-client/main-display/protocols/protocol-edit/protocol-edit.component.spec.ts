import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtocolEditComponent } from './protocol-edit.component';

describe('ProtocolEditComponent', () => {
  let component: ProtocolEditComponent;
  let fixture: ComponentFixture<ProtocolEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProtocolEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProtocolEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
