import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { StaffService } from 'src/app/entities/staff/staff.service';
@Injectable({
  providedIn: 'root',
})
export class StaffExtendedService extends StaffService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
