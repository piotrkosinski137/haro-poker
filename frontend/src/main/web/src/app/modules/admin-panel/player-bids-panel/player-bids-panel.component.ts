import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {RoundPlayer} from '../../../model/round-player';
import {GamePlayer} from '../../../model/game-player';

@Component({
  selector: 'app-player-bids-panel',
  templateUrl: './player-bids-panel.component.html',
  styleUrls: ['./player-bids-panel.component.scss']
})
export class PlayerBidsPanelComponent implements OnInit {

  balanceInputEnabled = false;
  @Input()
  roundPlayers: RoundPlayer[];
  @Input()
  gamePlayers: GamePlayer[];
  roundBidsTotal = 0;
  draftBidsTotal = 0;

  @Output()
  playerRoundBidsChanged = new EventEmitter<RoundPlayer[]>();

  constructor() {
  }

  ngOnInit() {
    this.roundPlayers.forEach(player => {
      this.roundBidsTotal += player.roundBid;
      this.draftBidsTotal += player.roundBid;
    });
  }

  enableBalanceInputs() {
    this.balanceInputEnabled = true;
  }

  onRoundBidsChangeSubmitted() {
    this.balanceInputEnabled = false;
    this.playerRoundBidsChanged.emit(this.roundPlayers);
  }

  onRoundBidChange(event$: any) {
    this.draftBidsTotal = 0;
    this.roundPlayers.forEach(player => {
      this.draftBidsTotal += player.roundBid;
    });
  }

  roundBidsDifference() {
    return this.roundBidsTotal - this.draftBidsTotal;
  }

  getPlayerName(id: string) {
    return this.gamePlayers.find(player => player.id === id).name;
  }
}
