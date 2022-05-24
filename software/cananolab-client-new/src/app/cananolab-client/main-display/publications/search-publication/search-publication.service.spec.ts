import { TestBed } from '@angular/core/testing';

import { SearchPublicationService } from './search-publication.service';

describe('SearchPublicationService', () => {
  let service: SearchPublicationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SearchPublicationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
