import {Component, Input, OnInit} from '@angular/core';

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
        const diff = Date.now() - this.gameTimestamp;
        this.seconds = Math.floor((diff / 1000) % 60);
        this.minutes = Math.floor((diff / (1000 * 60)) % 60);
        this.hours = Math.floor((diff / (1000 * 60 * 60)) % 24);
      }
    }, 1000);
  }

  ngOnInit() {
  }
}
