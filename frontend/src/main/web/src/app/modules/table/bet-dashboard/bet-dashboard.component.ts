import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-bet-dashboard',
  templateUrl: './bet-dashboard.component.html',
  styleUrls: ['./bet-dashboard.component.scss']
})
export class BetDashboardComponent implements OnInit {

  playerBalance = 5000;
  currentBet = 0;

  constructor() {
  }

  ngOnInit() {
  }

  betValueChanged($event: any) {
    this.currentBet = $event.target.value
  }

  hasNotBet() {
    return this.currentBet == 0;
  }
}
