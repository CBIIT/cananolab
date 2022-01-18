import { TestBed } from '@angular/core/testing';

import { SampleSearchResultsService } from './sample-search-results.service';

describe('SampleSearchResultsService', () => {
  let service: SampleSearchResultsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SampleSearchResultsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
