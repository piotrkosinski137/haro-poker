import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {GamePlayer} from '../../../model/game-player';
import {GamePlayerSocketService} from '../../../api/websocket/game-player-socket.service';
import {GamePlayerRestService} from '../../../api/rest/game-player-rest.service';
import {LocalStorageService} from '../../../api/local-storage.service';
import {GameRestService} from '../../../api/rest/game-rest.service';


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
    if (confirm('Would you like to be ' + this.manageActiveWord(!this.player.active) + ' in next round?')) {
      this.gamePlayerRestService.changeActiveState(!this.player.active);
      this.player.active = !this.player.active;
    }
  }

  manageActiveWord(active: boolean) {
    return active ? 'ACTIVE' : 'INACTIVE';
  }

  onGameStart() {
    if (confirm('Would you like to start new game? Previous game progress will be lost')) {
      this.gameRestService.startGame();
    }
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

  onBuyInClick() {
    this.gamePlayerRestService.buyIn();
  }

  hasZeroBalance() {
    if (this.player) {
      return this.player.balance === 0;
    }
  }
}
