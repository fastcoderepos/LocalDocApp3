import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IFilm } from './ifilm';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class FilmService extends GenericApiService<IFilm> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'film');
  }
}
