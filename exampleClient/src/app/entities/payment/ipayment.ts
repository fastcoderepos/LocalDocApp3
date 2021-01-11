export interface IPayment {
  amount: number;
  paymentDate: Date;
  paymentId: number;

  customerDescriptiveField?: number;
  customerId: number;
  rentalDescriptiveField?: number;
  rentalId: number;
  staffDescriptiveField?: number;
  staffId: number;
}
