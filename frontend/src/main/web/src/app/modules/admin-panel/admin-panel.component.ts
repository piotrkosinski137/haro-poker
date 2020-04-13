import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {RoundPlayerSocketService} from '../../api/websocket/round-player-socket.service';
import {RoundPlayer} from '../../model/round-player';
import {RoundPlayerRestService} from '../../api/rest/round-player-rest.service';
import {GamePlayer} from '../../model/game-player';
import {GamePlayerSocketService} from '../../api/websocket/game-player-socket.service';
import {GameSocketService} from '../../api/websocket/game-socket.service';
import {Game} from '../../model/game';
import {GamePlayerRestService} from '../../api/rest/game-player-rest.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit, OnDestroy {

  roundPlayerSubscription: Subscription;
  gamePlayerSubscription: Subscription;
  gameSubscription: Subscription;
  roundPlayers: RoundPlayer[];
  gamePlayers: GamePlayer[];
  game: Game;

  constructor(private roundPlayerSocketService: RoundPlayerSocketService,
              private gamePlayerSocketService: GamePlayerSocketService,
              private gameSocketService: GameSocketService,
              private gamePlayerRestService: GamePlayerRestService,
              private roundPlayerRestService: RoundPlayerRestService) {
  }

  ngOnInit() {
    this.gamePlayerSubscription = this.gamePlayerSocketService.getGamePlayers()
      .subscribe(gamePlayers => this.gamePlayers = gamePlayers);
    this.roundPlayerSubscription = this.roundPlayerSocketService.getRoundPlayers()
      .subscribe(roundPlayers => this.roundPlayers = roundPlayers);
    this.gameSubscription = this.gameSocketService.getGame()
      .subscribe(game => this.game = game);
  }

  onPlayerRoundBidsChanged(roundPlayers: RoundPlayer[]) {
    this.roundPlayerRestService.manualRoundBidsUpdate(roundPlayers);
  }

  ngOnDestroy() {
    this.roundPlayerSubscription.unsubscribe();
    this.gamePlayerSubscription.unsubscribe();
    this.gameSubscription.unsubscribe();
  }

  onPlayerRemoved(id: string) {

    if (confirm('Are you sure to remove player ' +
      this.gamePlayers.find(player => player.id === id).name)) {
      this.gamePlayerRestService.removePlayer(id);
    }
  }
}
