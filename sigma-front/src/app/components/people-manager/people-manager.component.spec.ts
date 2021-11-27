import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeopleManagerComponent } from './people-manager.component';

describe('PeopleManagerComponent', () => {
  let component: PeopleManagerComponent;
  let fixture: ComponentFixture<PeopleManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PeopleManagerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PeopleManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
