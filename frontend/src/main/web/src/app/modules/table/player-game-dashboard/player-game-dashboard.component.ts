import {Component, Input, OnInit} from '@angular/core';
import {Player} from "../../model/player";

@Component({
  selector: 'app-player-game-dashboard',
  templateUrl: './player-game-dashboard.component.html',
  styleUrls: ['./player-game-dashboard.component.scss']
})
export class PlayerGameDashboardComponent implements OnInit {

  @Input()
  player: Player;

  constructor() { }

  ngOnInit() {
  }

  hasTurn() {
    return this.player.hasTurn
  }
}
