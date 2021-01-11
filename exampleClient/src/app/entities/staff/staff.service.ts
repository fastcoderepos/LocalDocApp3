import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IStaff } from './istaff';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class StaffService extends GenericApiService<IStaff> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'staff');
  }
}
