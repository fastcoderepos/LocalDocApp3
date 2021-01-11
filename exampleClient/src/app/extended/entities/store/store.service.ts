import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { StoreService } from 'src/app/entities/store/store.service';
@Injectable({
  providedIn: 'root',
})
export class StoreExtendedService extends StoreService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
