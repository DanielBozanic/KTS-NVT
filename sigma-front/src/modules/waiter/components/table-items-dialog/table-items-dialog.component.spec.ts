import { ComponentFixture, TestBed } from "@angular/core/testing";
import { TableItemsDialogComponent } from "./table-items-dialog.component";

describe('TableItemsDialogComponent', () => {
    let component: TableItemsDialogComponent;
    let fixture: ComponentFixture<TableItemsDialogComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [TableItemsDialogComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(TableItemsDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});