import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {GamePlayer} from '../../model/game-player';
import {LocalStorageService} from '../../api/local-storage.service';
import {GameRestService} from '../../api/rest/game-rest.service';
import {GamePlayerSocketService} from '../../api/websocket/game-player-socket.service';
import {GamePlayerRestService} from '../../api/rest/game-player-rest.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  player: GamePlayer;

  constructor(private gamePlayerSocketService: GamePlayerSocketService,
              private gamePlayerRestService: GamePlayerRestService,
              private localStorageService: LocalStorageService,
              private gameRestService: GameRestService) {
  }

  ngOnInit() {
    this.subscription = this.localStorageService.sessionPlayerId.subscribe(sessionId =>
      this.gamePlayerSocketService.getSessionPlayer().subscribe(player => this.player = player));
  }

  onChangePlayerActiveStatusClick() {
    this.gamePlayerRestService.changeActiveState(!this.player.active);
  }

  onGameStart() {
    this.gameRestService.startGame();
  }

  isAdmin(tableNumber: number) {
    return tableNumber === 1;
  }

  onNewRoundClick() {
    this.gameRestService.newRound();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
