import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IUserrole } from './iuserrole';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class UserroleService extends GenericApiService<IUserrole> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'userrole');
  }
}
