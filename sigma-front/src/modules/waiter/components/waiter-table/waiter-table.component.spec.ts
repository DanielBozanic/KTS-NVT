import { ComponentFixture, TestBed } from "@angular/core/testing";
import { WaiterTableComponent } from "./waiter-table.component";

describe('WaiterTableComponent', () => {
    let component: WaiterTableComponent;
    let fixture: ComponentFixture<WaiterTableComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [WaiterTableComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(WaiterTableComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});