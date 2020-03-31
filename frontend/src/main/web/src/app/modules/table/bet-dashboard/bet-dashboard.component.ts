import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bet-dashboard',
  templateUrl: './bet-dashboard.component.html',
  styleUrls: ['./bet-dashboard.component.scss']
})
export class BetDashboardComponent implements OnInit {

  playerBalance = 5000;
  sliderCurrentBet = 0;
  inputCurrentBet = 0;

  constructor() { }

  ngOnInit() {
  }

  betSliderValueChanged($event: any) {
    this.sliderCurrentBet = $event.target.value
  }

  betInputValueChanged($event: any) {
    this.inputCurrentBet = $event.target.value
  }
}
