import {Component, Input, OnInit} from '@angular/core';
import {Time} from "../../../model/time";

@Component({
  selector: 'app-timer',
  templateUrl: './timer.component.html',
  styleUrls: ['./timer.component.scss']
})
export class TimerComponent implements OnInit {

  @Input()
  time: Time;

  constructor() { }

  ngOnInit() {
  }

}
