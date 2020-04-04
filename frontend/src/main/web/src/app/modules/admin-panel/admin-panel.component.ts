import {Component, OnDestroy, OnInit} from '@angular/core';
import {PlayerService} from "../../api/player.service";
import {Subscription} from "rxjs";
import {Player} from "../../model/player";
import {BalanceRequest} from "../../model/balance-request";

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit, OnDestroy {

  playerSubscription: Subscription;
  players: Player[];

  constructor(private playerService: PlayerService) {
  }

  ngOnInit() {
    this.playerSubscription = this.playerService.getPlayers()
      .subscribe(players => this.players = players);
  }

  ngOnDestroy() {
    this.playerSubscription.unsubscribe();
  }

  onPlayerBalancesChanged(balanceRequests: BalanceRequest[]) {
    this.playerService.updateBalances(balanceRequests);
  }
}
