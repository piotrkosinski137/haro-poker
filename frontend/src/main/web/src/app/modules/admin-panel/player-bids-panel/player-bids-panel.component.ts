import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RoundPlayer} from '../../../model/round-player';
import {GamePlayer} from '../../../model/game-player';

@Component({
  selector: 'app-player-bids-panel',
  templateUrl: './player-bids-panel.component.html',
  styleUrls: ['./player-bids-panel.component.scss']
})
export class PlayerBidsPanelComponent {

  _roundPlayers: RoundPlayer[];
  @Input() set roundPlayers(players: RoundPlayer[]) {
    this.roundBidsTotal = 0;
    this.draftBidsTotal = 0;
    players.forEach(player => {
      this.roundBidsTotal += player.roundBid;
      this.draftBidsTotal += player.roundBid;
    });
    this._roundPlayers = players;
  }
  @Input()
  gamePlayers: GamePlayer[];
  @Output()
  playerRoundBidsChanged = new EventEmitter<RoundPlayer[]>();
  @Output()
  playerRemoved = new EventEmitter<string>();

  roundBidsTotal = 0;
  draftBidsTotal = 0;
  balanceInputEnabled = false;

  constructor() {
  }

  enableBalanceInputs() {
    this.balanceInputEnabled = true;
  }

  onRoundBidsChangeSubmitted() {
    this.balanceInputEnabled = false;
    this.playerRoundBidsChanged.emit(this._roundPlayers);
  }

  onRoundBidChange(event$: any) {
    this.draftBidsTotal = 0;
    this._roundPlayers.forEach(player => {
      this.draftBidsTotal += player.roundBid;
    });
  }

  roundBidsDifference() {
    return this.roundBidsTotal - this.draftBidsTotal;
  }

  getPlayerName(id: string) {
    return this.gamePlayers.find(player => player.id === id).name;
  }

  onPlayerRemovedClick(id: string) {
    this.playerRemoved.emit(id);
  }
}
