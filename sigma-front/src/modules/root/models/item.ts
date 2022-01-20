export class Item {
  id!: number;
  buyingPrice!: number;
  sellingPrice!: number;
  description!: string;
  name!: string;
  state!: string;
  quantity!: number;
  itemId: number | undefined;
  employeeId: number | undefined;
  image!: string;
}
