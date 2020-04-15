import {Component, Input, OnInit} from '@angular/core';
import {GameRestService} from '../../../api/rest/game-rest.service';
import {Game} from '../../../model/game';

@Component({
  selector: 'app-blind-panel',
  templateUrl: './blind-panel.component.html',
  styleUrls: ['./blind-panel.component.scss']
})
export class BlindPanelComponent implements OnInit {

  @Input()
  game: Game;
  blindInputsEnabled = false;
  buttonEnabled = false;

  constructor(private gameService: GameRestService) { }

  ngOnInit() {
  }

  onChangeBlindClick() {
    this.blindInputsEnabled = false;
    this.buttonEnabled = false;
    this.gameService.updateBlinds(this.game.smallBlind);
  }

  enableBlindInputs() {
    this.blindInputsEnabled = true;
    this.buttonEnabled = true;
  }
}
