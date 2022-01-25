import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddItemInMenuDialogComponent } from './add-item-in-menu-dialog.component';

describe('AddItemInMenuDialogComponent', () => {
  let component: AddItemInMenuDialogComponent;
  let fixture: ComponentFixture<AddItemInMenuDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddItemInMenuDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddItemInMenuDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
