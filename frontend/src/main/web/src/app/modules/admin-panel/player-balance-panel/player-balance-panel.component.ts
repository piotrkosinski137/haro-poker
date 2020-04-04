import { Component, OnInit } from '@angular/core';
import {PlayerService} from "../../../api/player.service";
import {Observable} from "rxjs";
import {Player} from "../../../model/player";

@Component({
  selector: 'app-player-balance-panel',
  templateUrl: './player-balance-panel.component.html',
  styleUrls: ['./player-balance-panel.component.scss']
})
export class PlayerBalancePanelComponent implements OnInit {

  balanceInputEnabled = false;
  players$: Observable<Player[]>;

  constructor(private playerService: PlayerService) { }

  ngOnInit() {
    this.players$ = this.playerService.getPlayers();
  }

  disableBalanceInputs() {
    this.balanceInputEnabled = false;
  }

  enableBalanceInputs() {
    this.balanceInputEnabled = true;
  }

}
