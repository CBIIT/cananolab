import { TestBed } from '@angular/core/testing';

import { NanomaterialService } from './nanomaterial.service';

describe('NanomaterialService', () => {
  let service: NanomaterialService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NanomaterialService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
