import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtocolViewComponent } from './protocol-view.component';

describe('ProtocolViewComponent', () => {
  let component: ProtocolViewComponent;
  let fixture: ComponentFixture<ProtocolViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProtocolViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProtocolViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
