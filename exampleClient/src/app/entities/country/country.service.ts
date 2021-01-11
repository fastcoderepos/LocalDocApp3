import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ICountry } from './icountry';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class CountryService extends GenericApiService<ICountry> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'country');
  }
}
