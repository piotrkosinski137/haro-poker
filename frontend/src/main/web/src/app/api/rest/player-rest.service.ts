import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Player} from "../../model/player";
import {environment} from "../../../environments/environment";
import {PlayerSocketService} from "../websocket/player-socket.service";
import {map} from "rxjs/operators";
import {LocalStorageService} from "../local-storage.service";

@Injectable({
  providedIn: 'root'
})
export class PlayerRestService {

  constructor(private http: HttpClient, private playerSocketService: PlayerSocketService,
              private localStorageService: LocalStorageService) {
  }

  registerPlayer(playerName: string) {
    let params = new HttpParams().set('playerName', playerName);
    this.http.post(environment.PROXY_PATH + "players/add", null, {params: params}).subscribe(
      (response: any) => this.localStorageService.sessionId = String(response.id));
  }

  getPlayers(): Observable<Player[]> {
    return this.playerSocketService.getPlayers();
  }

  updateBalances(players: Player[]) {
    // TODO
  }

  getSessionPlayer() {
    return this.playerSocketService.getPlayers().pipe(
      map(players => players.find(player => player.id === this.localStorageService.sessionId)));
  }
}
