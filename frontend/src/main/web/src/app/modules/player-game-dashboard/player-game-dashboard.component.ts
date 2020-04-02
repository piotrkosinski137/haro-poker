import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-player-game-dashboard',
  templateUrl: './player-game-dashboard.component.html',
  styleUrls: ['./player-game-dashboard.component.scss']
})
export class PlayerGameDashboardComponent implements OnInit {

  @Input()
  hasTurn: boolean;

  constructor() { }

  ngOnInit() {
  }

  doesHaveTurn() {
    return this.hasTurn;
  }
}
