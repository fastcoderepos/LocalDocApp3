import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CategoryService } from 'src/app/entities/category/category.service';
@Injectable({
  providedIn: 'root',
})
export class CategoryExtendedService extends CategoryService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
