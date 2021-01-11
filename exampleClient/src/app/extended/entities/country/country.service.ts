import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CountryService } from 'src/app/entities/country/country.service';
@Injectable({
  providedIn: 'root',
})
export class CountryExtendedService extends CountryService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
