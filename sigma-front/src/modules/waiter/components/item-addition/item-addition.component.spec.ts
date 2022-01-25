import { ComponentFixture, TestBed } from "@angular/core/testing";
import { ItemAdditionComponent } from "./item-addition.component";

describe('ItemAdditionComponent', () => {
    let component: ItemAdditionComponent;
    let fixture: ComponentFixture<ItemAdditionComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ItemAdditionComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ItemAdditionComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});