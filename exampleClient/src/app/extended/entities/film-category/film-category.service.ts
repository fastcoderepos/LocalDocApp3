import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FilmCategoryService } from 'src/app/entities/film-category/film-category.service';
@Injectable({
  providedIn: 'root',
})
export class FilmCategoryExtendedService extends FilmCategoryService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
