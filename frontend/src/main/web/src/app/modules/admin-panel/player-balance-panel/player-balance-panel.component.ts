import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Player} from "../../../model/player";

@Component({
  selector: 'app-player-balance-panel',
  templateUrl: './player-balance-panel.component.html',
  styleUrls: ['./player-balance-panel.component.scss']
})
export class PlayerBalancePanelComponent implements OnInit {

  balanceInputEnabled = false;
  @Input()
  players: Player[];

  @Output()
  playerBalancesChanged = new EventEmitter<Player[]>();

  constructor() {
  }

  ngOnInit() {
  }

  enableBalanceInputs() {
    this.balanceInputEnabled = true;
  }

  onBalanceChangeSubmitted() {
    this.balanceInputEnabled = false;
    this.playerBalancesChanged.emit(this.players);
  }
}
