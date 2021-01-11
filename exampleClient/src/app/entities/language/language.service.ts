import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ILanguage } from './ilanguage';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class LanguageService extends GenericApiService<ILanguage> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'language');
  }
}
