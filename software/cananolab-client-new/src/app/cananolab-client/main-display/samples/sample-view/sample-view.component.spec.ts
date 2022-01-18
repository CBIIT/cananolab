import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SampleViewComponent } from './sample-view.component';

describe('SampleViewComponent', () => {
  let component: SampleViewComponent;
  let fixture: ComponentFixture<SampleViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SampleViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SampleViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
