import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IPayment } from './ipayment';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class PaymentService extends GenericApiService<IPayment> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'payment');
  }
}
