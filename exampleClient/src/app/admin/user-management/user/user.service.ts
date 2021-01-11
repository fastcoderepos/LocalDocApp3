import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IUser } from './iuser';
import { GenericApiService } from 'src/app/common/shared';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class UserService extends GenericApiService<IUser> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'user');
  }

  /**
   * Call api to update user profile.
   * @param item
   * @returns Observable of updated entity object.
   */
  public updateProfile(user: IUser): Observable<IUser> {
    return this.httpclient.put<IUser>(this.url + '/updateProfile', user).pipe(catchError(this.handleError));
  }

  getProfile() {
    return this.httpclient.get<IUser>(this.url + '/getProfile').pipe(
      map((response: any) => {
        return response;
      }),
      catchError(this.handleError)
    );
  }

  updateTheme(theme: string) {
    return this.httpclient.put(`${this.url}/updateTheme?theme=${theme}`, null).pipe(
      map((response: any) => {
        return response;
      }),
      catchError(this.handleError)
    );
  }

  updateLanguage(language: string) {
    return this.httpclient.put(`${this.url}/updateLanguage?language=${language}`, null).pipe(
      map((response: any) => {
        return response;
      }),
      catchError(this.handleError)
    );
  }
}
