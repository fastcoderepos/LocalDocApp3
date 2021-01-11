import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PaymentService } from 'src/app/entities/payment/payment.service';
@Injectable({
  providedIn: 'root',
})
export class PaymentExtendedService extends PaymentService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
