import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AddressService } from 'src/app/entities/address/address.service';
@Injectable({
  providedIn: 'root',
})
export class AddressExtendedService extends AddressService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
