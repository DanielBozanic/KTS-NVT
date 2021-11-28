import { Item } from "./item";

export class Order {
    id!: number;
    state!: string;
    items!: Item[];
}