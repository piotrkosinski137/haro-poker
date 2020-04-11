import {Component, Input, OnInit} from '@angular/core';
import * as moment from 'moment';

@Component({
  selector: 'app-timer',
  templateUrl: './timer.component.html',
  styleUrls: ['./timer.component.scss']
})
export class TimerComponent implements OnInit {

  @Input()
  gameTimestamp: number;

  seconds = 0;
  minutes = 0;
  hours = 0;

  constructor() {
    setInterval(() => {
      if (this.gameTimestamp !== 0) {
        const now = Date.now();
        const diffDuration = moment.duration(now - this.gameTimestamp);
        this.seconds = diffDuration.seconds();
        this.minutes = diffDuration.minutes();
        this.hours = diffDuration.hours();
      }
    }, 1000);
  }

  ngOnInit() {
  }
}
