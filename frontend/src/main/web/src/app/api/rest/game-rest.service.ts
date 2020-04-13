import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Card} from '../../model/card';
import {LocalStorageService} from '../local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class GameRestService {

  constructor(private http: HttpClient, private localStorageService: LocalStorageService) {
  }

  startGame() {
    return this.http.post(environment.PROXY_PATH + 'game/start', null).subscribe();
  }

  getPlayerCards(): Observable<Card[]> {
    const id = this.localStorageService.sessionId;
    if (id) {
      return this.http.get<Card[]>(environment.PROXY_PATH + 'player/' + id + '/cards');
    }
  }

  updateBlinds(small: number) {
    const params = new HttpParams().set('small', String(small));
    return this.http.put(environment.PROXY_PATH + 'game/blinds/update', null,
      {params}).subscribe();
  }
}
