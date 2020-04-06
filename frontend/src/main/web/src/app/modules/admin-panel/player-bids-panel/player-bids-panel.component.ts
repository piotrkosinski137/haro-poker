import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Player} from "../../../model/player";

@Component({
  selector: 'app-player-bids-panel',
  templateUrl: './player-bids-panel.component.html',
  styleUrls: ['./player-bids-panel.component.scss']
})
export class PlayerBidsPanelComponent implements OnInit {

  balanceInputEnabled = false;
  @Input()
  players: Player[];
  roundBidsTotal: number = 0;
  draftBidsTotal: number = 0;

  @Output()
  playerBalancesChanged = new EventEmitter<Player[]>();

  constructor() {
  }

  ngOnInit() {
    this.players.forEach(player => {
      this.roundBidsTotal+= player.roundBid;
      this.draftBidsTotal+= player.roundBid;
    });
  }

  enableBalanceInputs() {
    this.balanceInputEnabled = true;
  }

  onBalanceChangeSubmitted() {
    this.balanceInputEnabled = false;
    this.playerBalancesChanged.emit(this.players);
  }

  onRoundBidChange(event$: any) {
    this.draftBidsTotal = 0;
    this.players.forEach(player => {
      this.draftBidsTotal+= player.roundBid
    });
  }

  roundBidsDifference() {
    return this.roundBidsTotal - this.draftBidsTotal;
  }
}
