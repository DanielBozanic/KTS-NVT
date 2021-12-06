import { Item } from "./item";

export class Order {
    id!: number;
    state!: string;
    totalPrice!: number;
    items!: Item[];
}