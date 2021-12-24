import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaiterAddItemsComponent } from './waiter-addItems.component';

describe('WaiterAddItemsComponent', () => {
  let component: WaiterAddItemsComponent;
  let fixture: ComponentFixture<WaiterAddItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaiterAddItemsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WaiterAddItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
