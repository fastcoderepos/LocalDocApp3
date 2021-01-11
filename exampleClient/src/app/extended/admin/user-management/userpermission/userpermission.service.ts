import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserpermissionService } from 'src/app/admin/user-management/userpermission/userpermission.service';
@Injectable({
  providedIn: 'root',
})
export class UserpermissionExtendedService extends UserpermissionService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
