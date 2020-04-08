import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Player} from "../../model/player";
import {PlayerRestService} from "../../api/rest/player-rest.service";

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit, OnDestroy {

  playerSubscription: Subscription;
  players: Player[];

  constructor(private playerService: PlayerRestService) {
  }

  ngOnInit() {
    this.playerSubscription = this.playerService.getPlayers()
      .subscribe(players => this.players = players);
  }

  ngOnDestroy() {
    this.playerSubscription.unsubscribe();
  }

  onPlayerBalancesChanged(players: Player[]) {
    this.playerService.updateBalances(players);
  }
}
