import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ICategory } from './icategory';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class CategoryService extends GenericApiService<ICategory> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'category');
  }
}
