import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IRental } from './irental';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class RentalService extends GenericApiService<IRental> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'rental');
  }
}
