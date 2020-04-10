import {Component, OnInit} from '@angular/core';
import {RoundPlayerRestService} from "../../../api/rest/round-player-rest.service";

@Component({
  selector: 'app-bet-dashboard',
  templateUrl: './bet-dashboard.component.html',
  styleUrls: ['./bet-dashboard.component.scss']
})
export class BetDashboardComponent implements OnInit {

  playerBalance = 5000;
  currentBet = 0;

  constructor(private roundPlayerRestService: RoundPlayerRestService) {
  }

  ngOnInit() {
  }

  betValueChanged($event: any) {
    this.currentBet = $event.target.value
  }

  hasNotBet() {
    return this.currentBet == 0;
  }

  onBetClick(amount: any) {
    this.roundPlayerRestService.bid(amount);
  }
}
