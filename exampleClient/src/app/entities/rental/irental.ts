export interface IRental {
  lastUpdate: Date;
  rentalDate: Date;
  rentalId: number;
  returnDate?: Date;

  customerDescriptiveField?: number;
  customerId: number;
  inventoryDescriptiveField?: number;
  inventoryId: number;
  staffDescriptiveField?: number;
  staffId: number;
}
