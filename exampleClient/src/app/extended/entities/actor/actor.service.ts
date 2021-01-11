import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActorService } from 'src/app/entities/actor/actor.service';
@Injectable({
  providedIn: 'root',
})
export class ActorExtendedService extends ActorService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
