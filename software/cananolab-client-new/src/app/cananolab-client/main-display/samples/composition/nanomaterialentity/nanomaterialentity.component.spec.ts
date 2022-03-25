import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NanomaterialentityComponent } from './nanomaterialentity.component';

describe('NanomaterialentityComponent', () => {
  let component: NanomaterialentityComponent;
  let fixture: ComponentFixture<NanomaterialentityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NanomaterialentityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NanomaterialentityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
