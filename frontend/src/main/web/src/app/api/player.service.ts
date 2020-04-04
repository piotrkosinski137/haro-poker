import {Injectable} from '@angular/core';
import {Player} from "../model/player";
import {Observable, of} from "rxjs";
import {PlayerPosition} from "../model/player-position";

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  players = [
    new Player(1, 'Kosa', 9430, 0, false, PlayerPosition.NONE, false),
    new Player(2, 'Toms', 2300, 0, false, PlayerPosition.DEALER, false),
    new Player(3, 'Dziku', 11130, 100, false, PlayerPosition.SMALL_BLIND, false),
    new Player(4, 'Magier', 7330, 200, false, PlayerPosition.BIG_BLIND, false),
    new Player(5, 'Larson', 700, 0, false, PlayerPosition.NONE, true),
    new Player(6, 'Demon', 8110, 0, true, PlayerPosition.NONE, false),
    new Player(7, 'Kawa', 2100, 0, false, PlayerPosition.NONE, false)
  ];

  constructor() {
  }

  getPlayers(): Observable<Player[]> {
    return of(this.players);
  }

  updateBalances(players: Player[]) {
    this.players = players
  }
}
