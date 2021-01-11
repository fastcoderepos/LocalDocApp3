import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ICity } from './icity';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class CityService extends GenericApiService<ICity> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'city');
  }
}
