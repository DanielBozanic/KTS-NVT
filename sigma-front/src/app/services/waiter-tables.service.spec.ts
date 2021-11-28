import { TestBed } from '@angular/core/testing';

import { WaiterTablesService } from './waiter-tables.service';

describe('WaiterTablesService', () => {
  let service: WaiterTablesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WaiterTablesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});