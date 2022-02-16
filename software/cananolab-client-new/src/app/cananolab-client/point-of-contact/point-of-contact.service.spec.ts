import { TestBed } from '@angular/core/testing';

import { PointOfContactService } from './point-of-contact.service';

describe('PointOfContactService', () => {
  let service: PointOfContactService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PointOfContactService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
