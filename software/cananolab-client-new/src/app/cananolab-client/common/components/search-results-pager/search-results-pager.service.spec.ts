import { TestBed } from '@angular/core/testing';

import { SearchResultsPagerService } from './search-results-pager.service';

describe('SearchResultsPagerService', () => {
  let service: SearchResultsPagerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SearchResultsPagerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
