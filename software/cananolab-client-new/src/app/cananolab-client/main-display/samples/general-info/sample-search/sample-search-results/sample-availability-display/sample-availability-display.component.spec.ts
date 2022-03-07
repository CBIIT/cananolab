import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SampleAvailabilityDisplayComponent } from './sample-availability-display.component';

describe('SampleAvailabilityDisplayComponent', () => {
  let component: SampleAvailabilityDisplayComponent;
  let fixture: ComponentFixture<SampleAvailabilityDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SampleAvailabilityDisplayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SampleAvailabilityDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
