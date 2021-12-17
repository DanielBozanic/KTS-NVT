import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaiterOrderComponent } from './waiter-order.component';

describe('FoodDrinksManagerComponent', () => {
  let component: WaiterOrderComponent;
  let fixture: ComponentFixture<WaiterOrderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaiterOrderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WaiterOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
