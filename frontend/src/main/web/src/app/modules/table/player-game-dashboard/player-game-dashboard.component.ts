import {Component, Input, OnInit} from '@angular/core';
import {GamePlayer} from "../../../model/game-player";
import {RoundPlayer} from "../../../model/round-player";
import {LocalStorageService} from "../../../api/local-storage.service";

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

  constructor(private localStorageService: LocalStorageService) {
  }

  ngOnInit() {
    // if (this.localStorageService.isSessionPlayer(this.gamePlayer.id)) {
    //   // TODO get cards from private channel
    // }
  }

  isSessionPlayer() {
    return this.localStorageService.isSessionPlayer(this.gamePlayer.id)
  }

  isActive() {
    return this.gamePlayer.active;
  }
}
