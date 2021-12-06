import { TestBed } from '@angular/core/testing';

import { PeopleManagerService } from './people-manager.service';

describe('PeopleManagerService', () => {
  let service: PeopleManagerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PeopleManagerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
