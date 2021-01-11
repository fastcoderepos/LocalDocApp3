import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IFilmCategory } from './ifilm-category';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class FilmCategoryService extends GenericApiService<IFilmCategory> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'filmCategory');
  }
}
