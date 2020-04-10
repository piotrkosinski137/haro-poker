import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {RoundPlayerSocketService} from "../../api/websocket/round-player-socket.service";
import {RoundPlayer} from "../../model/round-player";
import {RoundPlayerRestService} from "../../api/rest/round-player-rest.service";
import {GamePlayer} from "../../model/game-player";
import {GamePlayerSocketService} from "../../api/websocket/game-player-socket.service";

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit, OnDestroy {

  roundPlayerSubscription: Subscription;
  gamePlayerSubscription: Subscription;
  roundPlayers: RoundPlayer[];
  gamePlayers: GamePlayer[];

  constructor(private roundPlayerSocketService: RoundPlayerSocketService,
              private gamePlayerSocketService: GamePlayerSocketService,
              private roundPlayerRestService: RoundPlayerRestService) {
  }

  ngOnInit() {
    this.gamePlayerSubscription = this.gamePlayerSocketService.getGamePlayers()
      .subscribe(gamePlayers => this.gamePlayers = gamePlayers);
    this.roundPlayerSubscription = this.roundPlayerSocketService.getRoundPlayers()
      .subscribe(roundPlayers => this.roundPlayers = roundPlayers);
  }

  onPlayerRoundBidsChanged(roundPlayers: RoundPlayer[]) {
    this.roundPlayerRestService.manualRoundBidsUpdate(roundPlayers);
  }

  ngOnDestroy() {
    this.roundPlayerSubscription.unsubscribe();
    this.gamePlayerSubscription.unsubscribe();
  }
}
