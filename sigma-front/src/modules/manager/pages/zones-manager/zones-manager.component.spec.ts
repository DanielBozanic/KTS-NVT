import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZonesManagerComponent } from './zones-manager.component';

describe('ZonesManagerComponent', () => {
  let component: ZonesManagerComponent;
  let fixture: ComponentFixture<ZonesManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZonesManagerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZonesManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
