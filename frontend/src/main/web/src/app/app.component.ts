import {Component, OnDestroy, OnInit} from '@angular/core';
import {PlayerSocketService} from "./api/websocket/player-socket.service";
import {PlayerRestService} from "./api/rest/player-rest.service";
import {Subscription} from "rxjs";
import {Player} from "./model/player";
import {LocalStorageService} from "./api/local-storage.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  sessionPlayer: Player;

  constructor(private playerSocketService: PlayerSocketService, private playerService: PlayerRestService,
              private localStorageService: LocalStorageService) {
  }

  ngOnInit(): void {
    this.subscription = this.localStorageService.sessionPlayerId.subscribe(sessionId =>
      this.playerService.getSessionPlayer().subscribe(player => this.sessionPlayer = player));
  }

  isAdmin(tableNumer: number) {
    return tableNumer === 1;
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
