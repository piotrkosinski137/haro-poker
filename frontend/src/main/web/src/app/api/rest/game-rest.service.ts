import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import {EMPTY, Observable} from 'rxjs';
import {Card} from '../../model/card';
import {LocalStorageService} from '../local-storage.service';
import {switchMap} from 'rxjs/operators';
import {PlayerMoney} from '../../model/player-money';
import {RoundPlayerSocketService} from '../websocket/round-player-socket.service';

@Injectable({
  providedIn: 'root'
})
export class GameRestService {

  constructor(private http: HttpClient, private localStorageService: LocalStorageService,
              private roundPlayerSocketService: RoundPlayerSocketService) {
  }

  startGame() {
    return this.http.post(environment.PROXY_PATH + 'game/start', null).subscribe();
  }

  finishRound(id: string) {
    return this.http.post(environment.PROXY_PATH + 'game/round/finish/' + id, null).subscribe();
  }

  manualUpdateBalances(money: PlayerMoney[]) {
    return this.http.put(environment.PROXY_PATH + 'game/admin/round-bids/update', {playerMoney: money}).subscribe();
  }

  getPlayerCards(): Observable<Card[]> {
    return this.roundPlayerSocketService.getSessionPlayer().pipe(switchMap(
      roundPlayer => {
        if (roundPlayer) {
          return this.http.get<Card[]>(environment.PROXY_PATH + 'player/' + roundPlayer.id + '/cards');
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
