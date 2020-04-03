import {Injectable} from '@angular/core';
import {Player} from "../model/player";
import {Observable, of} from "rxjs";
import {PlayerPosition} from "../model/player-position";

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  players = [
    new Player(1, 'Kosa', 9430, 0, true, PlayerPosition.NONE),
    new Player(2, 'Toms', 2300, 300, false, PlayerPosition.DEALER),
    new Player(3, 'Dziku', 11130, 100, false, PlayerPosition.SMALL_BLIND),
    new Player(4, 'Magier', 7330, 200, false, PlayerPosition.BIG_BLIND),
    new Player(5, 'Larson', 700, 600, false, PlayerPosition.NONE),
    new Player(6, 'Demon', 8110, 600, false, PlayerPosition.NONE),
    new Player(7, 'Kawa', 2100, 600, false, PlayerPosition.NONE)
  ];

  constructor() {
  }

  getPlayers(): Observable<Player[]> {
    return of(this.players);
  }
}
