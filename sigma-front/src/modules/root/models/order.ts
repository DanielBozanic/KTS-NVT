import { Item } from "./item";

export class Order {
    id!: number;
    tableId!: number;
    state!: string;
    totalPrice!: number;
    items!: Item[];
}