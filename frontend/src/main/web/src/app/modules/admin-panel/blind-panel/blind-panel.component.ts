import {Component, Input, OnInit} from '@angular/core';
import {GameService} from "../../../api/rest/game.service";
import {Game} from "../../../model/game";

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

  constructor(private gameService: GameService) { }

  ngOnInit() {
  }

  onChangeBlindClick(small: string) {
    this.blindInputsEnabled = false;
    this.buttonEnabled = false;
    this.gameService.updateBlinds(+small);
  }

  enableBlindInputs() {
    this.blindInputsEnabled = true;
    this.buttonEnabled = true;
  }
}
