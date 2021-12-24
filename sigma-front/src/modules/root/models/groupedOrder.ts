import { Table } from "./table";

export class GroupedOrder {
    id!: number;
    state!: string;
    totalPrice!: number;
    itemsByQuantity!: Map<string, number>;
    table!: Table;
}