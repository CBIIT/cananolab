import { TestBed } from '@angular/core/testing';

import { ProtocolosService } from './protocolos.service';

describe('ProtocolosService', () => {
  let service: ProtocolosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProtocolosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
