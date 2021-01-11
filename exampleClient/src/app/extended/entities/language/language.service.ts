import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LanguageService } from 'src/app/entities/language/language.service';
@Injectable({
  providedIn: 'root',
})
export class LanguageExtendedService extends LanguageService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
