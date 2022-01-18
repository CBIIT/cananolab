import { TestBed } from '@angular/core/testing';

import { StatusDisplayService } from './status-display.service';

describe('StatusDisplayService', () => {
  let service: StatusDisplayService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatusDisplayService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
