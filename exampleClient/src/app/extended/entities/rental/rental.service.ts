import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RentalService } from 'src/app/entities/rental/rental.service';
@Injectable({
  providedIn: 'root',
})
export class RentalExtendedService extends RentalService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
