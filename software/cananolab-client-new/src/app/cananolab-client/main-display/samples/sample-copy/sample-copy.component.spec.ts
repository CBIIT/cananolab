import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SampleCopyComponent } from './sample-copy.component';

describe('SampleCopyComponent', () => {
  let component: SampleCopyComponent;
  let fixture: ComponentFixture<SampleCopyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SampleCopyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SampleCopyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
