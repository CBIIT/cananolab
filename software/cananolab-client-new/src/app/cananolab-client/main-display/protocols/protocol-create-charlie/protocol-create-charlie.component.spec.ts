import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtocolCreateCharlieComponent } from './protocol-create-charlie.component';

describe('ProtocolCreateCharlieComponent', () => {
  let component: ProtocolCreateCharlieComponent;
  let fixture: ComponentFixture<ProtocolCreateCharlieComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProtocolCreateCharlieComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProtocolCreateCharlieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
