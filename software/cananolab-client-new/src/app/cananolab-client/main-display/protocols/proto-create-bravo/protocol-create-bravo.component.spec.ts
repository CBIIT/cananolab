import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtocolCreateBravoComponent } from './protocol-create-bravo.component';

describe('ProtoCreateBravoComponent', () => {
  let component: ProtocolCreateBravoComponent;
  let fixture: ComponentFixture<ProtocolCreateBravoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProtocolCreateBravoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProtocolCreateBravoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
