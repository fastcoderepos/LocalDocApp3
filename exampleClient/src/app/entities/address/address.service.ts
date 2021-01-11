import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IAddress } from './iaddress';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class AddressService extends GenericApiService<IAddress> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'address');
  }
}
