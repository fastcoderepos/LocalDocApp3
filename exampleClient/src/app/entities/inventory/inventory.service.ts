import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IInventory } from './iinventory';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class InventoryService extends GenericApiService<IInventory> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'inventory');
  }
}
