import { TestBed } from '@angular/core/testing';

import { SampleAdvancedSearchService } from './sample-advanced-search.service';

describe('SampleAdvancedSearchService', () => {
  let service: SampleAdvancedSearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SampleAdvancedSearchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
