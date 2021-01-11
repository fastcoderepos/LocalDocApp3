import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IActor } from './iactor';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class ActorService extends GenericApiService<IActor> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'actor');
  }
}
