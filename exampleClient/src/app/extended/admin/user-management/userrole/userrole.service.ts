import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserroleService } from 'src/app/admin/user-management/userrole/userrole.service';
@Injectable({
  providedIn: 'root',
})
export class UserroleExtendedService extends UserroleService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
