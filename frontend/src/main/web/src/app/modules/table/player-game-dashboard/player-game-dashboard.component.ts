import {Component, Input, OnInit} from '@angular/core';
import {GamePlayer} from "../../../model/game-player";
import {RoundPlayer} from "../../../model/round-player";

@Component({
  selector: 'app-player-game-dashboard',
  templateUrl: './player-game-dashboard.component.html',
  styleUrls: ['./player-game-dashboard.component.scss']
})
export class PlayerGameDashboardComponent implements OnInit {

  @Input()
  gamePlayer: GamePlayer;
  @Input()
  roundPlayer: RoundPlayer;

  constructor() {
  }

  ngOnInit() {
    if (this.isSessionPlayer()) {
      // TODO get cards from private channel
    }
  }

  isSessionPlayer() {
    return this.gamePlayer.id === localStorage.getItem('playerId');
  }

  isActive() {
    return this.gamePlayer.active;
  }
}
