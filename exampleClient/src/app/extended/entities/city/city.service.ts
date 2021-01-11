import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CityService } from 'src/app/entities/city/city.service';
@Injectable({
  providedIn: 'root',
})
export class CityExtendedService extends CityService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
