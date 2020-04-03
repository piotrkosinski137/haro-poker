import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-blind-panel',
  templateUrl: './blind-panel.component.html',
  styleUrls: ['./blind-panel.component.scss']
})
export class BlindPanelComponent implements OnInit {

  blindInputsEnabled = false;

  constructor() { }

  ngOnInit() {
  }

  disableBlindInputs() {
    this.blindInputsEnabled = false;
  }

  enableBlindInputs() {
    this.blindInputsEnabled = true;
  }
}
