import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SamplePublicationsComponent } from './sample-publications.component';

describe('SamplePublicationsComponent', () => {
  let component: SamplePublicationsComponent;
  let fixture: ComponentFixture<SamplePublicationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SamplePublicationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SamplePublicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
