import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtocolEditBravoComponent } from './protocol-edit-bravo.component';

describe('ProtocolEditBravoComponent', () => {
  let component: ProtocolEditBravoComponent;
  let fixture: ComponentFixture<ProtocolEditBravoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProtocolEditBravoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProtocolEditBravoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
