import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PointOfContactCreateComponent } from './point-of-contact-create.component';

describe('PointOfContactCreateComponent', () => {
  let component: PointOfContactCreateComponent;
  let fixture: ComponentFixture<PointOfContactCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PointOfContactCreateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PointOfContactCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
