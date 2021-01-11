import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IUserpermission } from './iuserpermission';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class UserpermissionService extends GenericApiService<IUserpermission> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'userpermission');
  }
}
