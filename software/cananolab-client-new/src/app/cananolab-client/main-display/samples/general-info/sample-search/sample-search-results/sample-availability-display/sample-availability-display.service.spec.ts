import { TestBed } from '@angular/core/testing';

import { SampleAvailabilityDisplayService } from './sample-availability-display.service';

describe('SampleAvailabilityDisplayService', () => {
  let service: SampleAvailabilityDisplayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SampleAvailabilityDisplayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
