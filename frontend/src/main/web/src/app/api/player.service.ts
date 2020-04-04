import {Injectable} from '@angular/core';
import {Player} from "../model/player";
import {Observable, of} from "rxjs";
import {PlayerPosition} from "../model/player-position";

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  players = [
    new Player(2, 'Dealer Toms', 2300, 0, false, PlayerPosition.DEALER, false, true),
    new Player(3, 'Small Dziku', 11130, 100, false, PlayerPosition.SMALL_BLIND, false, true),
    new Player(4, 'Big Magier', 7330, 200, false, PlayerPosition.BIG_BLIND, false, true),
    new Player(5, 'Folded Larson', 700, 0, false, PlayerPosition.NONE, true, true),
    new Player(6, 'Current Demon', 8110, 0, true, PlayerPosition.NONE, false, true),
    new Player(7, 'Inactive Kawa', 2100, 0, false, PlayerPosition.NONE, false, false)
  ];

  constructor() {
  }

  getPlayers(): Observable<Player[]> {
    return of(this.players);
  }

  updateBalances(players: Player[]) {
    this.players = players
  }

  addPlayer(playerName: string) {
    for (let i = 1; i <= 7; i++) {
      if (!this.players.find(player => player.id === i)) {
        this.players.push(
          new Player(i, playerName, 10000, 0, false, PlayerPosition.NONE, false, true));
        localStorage.setItem("playerId", i.toString());
        break;
      }
    }
  }

  getSessionPlayer() {
    return of(this.players.find(player => {
      return player.id.toString() == localStorage.getItem("playerId")
    }))
  }
}
