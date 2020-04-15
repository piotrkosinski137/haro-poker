import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {LocalStorageService} from '../local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class GamePlayerRestService {

  constructor(private http: HttpClient, private localStorageService: LocalStorageService) {
  }

  registerGamePlayer(playerName: string) {
    const params = new HttpParams().set('playerName', playerName);
    this.http.post(environment.PROXY_PATH + 'player/add', null, {params}).subscribe(
      (response: any) => this.localStorageService.sessionId = String(response.id));
  }

  changeActiveState(isActive: boolean) {
    const params = new HttpParams().set('isActive', String(isActive));
    this.http.post(environment.PROXY_PATH + 'player/' + this.localStorageService.sessionId + '/activation-status'
      , null, {params}).subscribe();
  }

  removePlayer(id: string) {
    this.http.delete(environment.PROXY_PATH + 'player/' + id + '/remove').subscribe();
  }

  buyIn() {
    this.http.post(environment.PROXY_PATH + 'player/' + this.localStorageService.sessionId + '/buy-in'
      , null).subscribe();
  }
}
