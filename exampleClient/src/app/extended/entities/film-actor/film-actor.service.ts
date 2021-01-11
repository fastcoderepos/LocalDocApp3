import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FilmActorService } from 'src/app/entities/film-actor/film-actor.service';
@Injectable({
  providedIn: 'root',
})
export class FilmActorExtendedService extends FilmActorService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
