import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Player} from "../../../model/player";
import {BalanceRequest} from "../../../model/balance-request";

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
  playerBalancesChanged = new EventEmitter<BalanceRequest[]>();

  constructor() {
  }

  ngOnInit() {
  }

  enableBalanceInputs() {
    this.balanceInputEnabled = true;
  }

  onBalanceChangeSubmitted(form: any) {
    this.balanceInputEnabled = false;
    this.playerBalancesChanged.emit(
      form._directives.map(ngModel => new BalanceRequest(ngModel.name, ngModel.model))
    )
  }
}
