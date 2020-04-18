import {Component, Input, OnInit} from '@angular/core';
import {RoundPlayerRestService} from '../../../api/rest/round-player-rest.service';
import {RoundPlayer} from '../../../model/round-player';

@Component({
  selector: 'app-bet-dashboard',
  templateUrl: './bet-dashboard.component.html',
  styleUrls: ['./bet-dashboard.component.scss']
})
export class BetDashboardComponent implements OnInit {

  @Input()
  sessionPlayer: RoundPlayer;
  @Input()
  minBet: number;
  currentBet = 0;

  constructor(private roundPlayerRestService: RoundPlayerRestService) {
  }

  ngOnInit() {
  }

  betValueChanged($event: any) {
    this.currentBet = $event.target.value;
  }

  onBetClick(amount: number, balance: number) {
    if (amount === balance) {
      this.onAllInClick();
    } else {
      this.roundPlayerRestService.bid(amount);
      this.currentBet = 0;
    }
  }

  onAllInClick() {
    this.roundPlayerRestService.allIn();
  }

  onFoldClick() {
    this.roundPlayerRestService.fold();
  }

  notEnoughFundsToBet() {
    return this.sessionPlayer.balance < (this.minBet - this.sessionPlayer.turnBid);
  }

  cantCheck() {
    return this.sessionPlayer.turnBid !== this.minBet;
  }
}
