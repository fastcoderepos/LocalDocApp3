export interface IStaff {
  active: boolean;
  email?: string;
  firstName: string;
  lastName: string;
  lastUpdate: Date;
  password?: string;
  staffId: number;
  storeId?: number;
  username: string;

  addressDescriptiveField?: number;
  addressId: number;
}
