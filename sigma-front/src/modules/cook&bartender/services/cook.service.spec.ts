import { TestBed } from '@angular/core/testing';

import { CookBartenderService } from './cook&bartender.service';

describe('CookManagerService', () => {
  let service: CookBartenderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CookBartenderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
