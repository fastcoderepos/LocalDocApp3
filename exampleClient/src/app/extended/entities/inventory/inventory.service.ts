import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { InventoryService } from 'src/app/entities/inventory/inventory.service';
@Injectable({
  providedIn: 'root',
})
export class InventoryExtendedService extends InventoryService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
