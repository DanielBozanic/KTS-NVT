import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FoodDrinksManagerComponent } from './food-drinks-manager.component';

describe('FoodDrinksManagerComponent', () => {
  let component: FoodDrinksManagerComponent;
  let fixture: ComponentFixture<FoodDrinksManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FoodDrinksManagerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodDrinksManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
