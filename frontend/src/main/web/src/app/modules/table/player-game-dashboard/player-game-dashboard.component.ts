import {Component, Input, OnInit} from '@angular/core';
import {GamePlayer} from '../../../model/game-player';
import {RoundPlayer} from '../../../model/round-player';
import {LocalStorageService} from '../../../api/local-storage.service';
import {RoundSocketService} from '../../../api/websocket/round-socket.service';
import {Card} from '../../../model/card';
import {Observable} from 'rxjs';

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
  @Input()
  isAdmin: boolean;
  playerCards$: Observable<Card[]>;

  constructor(private localStorageService: LocalStorageService, private roundSocketService: RoundSocketService) {
  }

  ngOnInit() {
    this.playerCards$ = this.roundSocketService.getPlayerCardsSubject();
  }

  isSessionPlayer() {
    return this.localStorageService.isSessionPlayer(this.gamePlayer.id);
  }

  isActive() {
    return this.roundPlayer !== undefined;
  }
}
