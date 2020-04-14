import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {LocalStorageService} from '../local-storage.service';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RoundPlayerRestService {

  constructor(private http: HttpClient, private localStorageService: LocalStorageService) {
  }

  bid(amount: number) {
    const params = new HttpParams().set('amount', String(amount));
    return this.http.post(environment.PROXY_PATH + 'player/' + this.localStorageService.sessionId + '/bid',
      null, {params}).subscribe();
  }

  fold() {
    return this.http.post(environment.PROXY_PATH + 'player/' + this.localStorageService.sessionId + '/fold',
      null).subscribe();
  }
}
