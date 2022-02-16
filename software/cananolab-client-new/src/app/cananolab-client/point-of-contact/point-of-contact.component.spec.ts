import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PointOfContactComponent } from './point-of-contact.component';

describe('PointOfContactComponent', () => {
  let component: PointOfContactComponent;
  let fixture: ComponentFixture<PointOfContactComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PointOfContactComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PointOfContactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
