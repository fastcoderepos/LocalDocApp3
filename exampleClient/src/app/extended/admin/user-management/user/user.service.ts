import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from 'src/app/admin/user-management/user/user.service';
@Injectable({
  providedIn: 'root',
})
export class UserExtendedService extends UserService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
