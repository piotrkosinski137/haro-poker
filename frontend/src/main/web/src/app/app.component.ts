import {Component, OnDestroy, OnInit} from '@angular/core';
import {GamePlayerSocketService} from './api/websocket/game-player-socket.service';
import {Subscription} from 'rxjs';
import {GamePlayer} from './model/game-player';
import {LocalStorageService} from './api/local-storage.service';
import {GameSocketService} from './api/websocket/game-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  sessionPlayer: GamePlayer;

  constructor(private gamePlayerSocketService: GamePlayerSocketService,
              private gameSocketService: GameSocketService,
              private localStorageService: LocalStorageService) {
  }

  ngOnInit(): void {
    this.subscription = this.localStorageService.sessionPlayerId.subscribe(sessionId =>
      this.gamePlayerSocketService.getSessionPlayer().subscribe(player => this.sessionPlayer = player));
  }

  isAdmin(tableNumber: number) {
    return tableNumber === 1;
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
