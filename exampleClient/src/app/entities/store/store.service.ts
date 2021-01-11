import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IStore } from './istore';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class StoreService extends GenericApiService<IStore> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'store');
  }
}
