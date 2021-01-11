export interface IAddress {
  address: string;
  address2?: string;
  addressId: number;
  district: string;
  lastUpdate: Date;
  phone: string;
  postalCode?: string;

  cityDescriptiveField?: number;
  cityId: number;
}
