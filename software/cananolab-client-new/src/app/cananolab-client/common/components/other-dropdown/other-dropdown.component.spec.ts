import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OtherDropdownComponent } from './other-dropdown.component';

describe('OtherDropdownComponent', () => {
  let component: OtherDropdownComponent;
  let fixture: ComponentFixture<OtherDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OtherDropdownComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OtherDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
