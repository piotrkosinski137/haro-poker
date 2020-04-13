import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import {EMPTY, Observable} from 'rxjs';
import {Card} from '../../model/card';
import {LocalStorageService} from '../local-storage.service';
import {GamePlayerSocketService} from '../websocket/game-player-socket.service';
import {switchMap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GameRestService {

  constructor(private http: HttpClient, private localStorageService: LocalStorageService,
              private gamePlayerSocketService: GamePlayerSocketService) {
  }

  startGame() {
    return this.http.post(environment.PROXY_PATH + 'game/start', null).subscribe();
  }

  finishRound(id: string) {
    return this.http.post(environment.PROXY_PATH + 'game/round/finish/' + id, null).subscribe();
  }

  getPlayerCards(): Observable<Card[]> {
    return this.gamePlayerSocketService.getSessionPlayer().pipe(switchMap(
      gamePlayer => {
        const id = this.localStorageService.sessionId;
        if (id && gamePlayer.active) {
          return this.http.get<Card[]>(environment.PROXY_PATH + 'player/' + id + '/cards');
        } else {
          return EMPTY;
        }
      }
    ));
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
