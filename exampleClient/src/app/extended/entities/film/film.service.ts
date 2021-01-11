import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FilmService } from 'src/app/entities/film/film.service';
@Injectable({
  providedIn: 'root',
})
export class FilmExtendedService extends FilmService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
