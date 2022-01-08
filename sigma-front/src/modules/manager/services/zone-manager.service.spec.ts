import { TestBed } from '@angular/core/testing';

import { ZoneManagerService } from './zone-manager.service';

describe('ZoneManagerService', () => {
  let service: ZoneManagerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ZoneManagerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
