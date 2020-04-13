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

  finishRound(id: string) {
    return this.http.post(environment.PROXY_PATH + 'game/round/finish/' + id, null).subscribe();
  }

  getPlayerCards(): Observable<Card[]> {
    return this.http.get<Card[]>(environment.PROXY_PATH + 'player/' +
      this.localStorageService.sessionId + '/cards');
  }

  updateBlinds(small: number) {
    const params = new HttpParams().set('small', String(small));
    return this.http.put(environment.PROXY_PATH + 'game/blinds/update', null,
      {params}).subscribe();
  }

  newRound() {
    return this.http.post(environment.PROXY_PATH + 'game/round/new', null).subscribe();
  }
}
