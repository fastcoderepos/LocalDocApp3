export interface IInventory {
  inventoryId: number;
  lastUpdate: Date;
  storeId?: number;

  filmDescriptiveField?: number;
  filmId: number;
}
