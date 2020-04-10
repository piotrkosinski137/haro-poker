import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {LocalStorageService} from "../local-storage.service";

@Injectable({
  providedIn: 'root'
})
export class GamePlayerRestService {

  constructor(private http: HttpClient, private localStorageService: LocalStorageService) {
  }

  registerGamePlayer(playerName: string) {
    let params = new HttpParams().set('playerName', playerName);
    this.http.post(environment.PROXY_PATH + "players/add", null, {params: params}).subscribe(
      (response: any) => this.localStorageService.sessionId = String(response.id));
  }

  changeActiveState() {
    // TODO post with playerId, that's it
  }
}
