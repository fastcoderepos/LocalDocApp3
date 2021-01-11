import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IFilmActor } from './ifilm-actor';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class FilmActorService extends GenericApiService<IFilmActor> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'filmActor');
  }
}
