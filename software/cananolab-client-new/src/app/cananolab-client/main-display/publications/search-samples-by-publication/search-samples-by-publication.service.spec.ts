import { TestBed } from '@angular/core/testing';

import { SearchSamplesByPublicationService } from './search-samples-by-publication.service';

describe('SearchSamplesByPublicationService', () => {
  let service: SearchSamplesByPublicationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SearchSamplesByPublicationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
