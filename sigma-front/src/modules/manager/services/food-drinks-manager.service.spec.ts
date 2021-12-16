import { TestBed } from '@angular/core/testing';

import { FoodDrinksManagerService } from './food-drinks-manager.service';

describe('FoodDrinksManagerService', () => {
  let service: FoodDrinksManagerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FoodDrinksManagerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
