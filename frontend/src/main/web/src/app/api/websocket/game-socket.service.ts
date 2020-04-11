import {Injectable, OnInit} from '@angular/core';
import {MessagingService} from "./messaging.service";
import {Message} from "@stomp/stompjs";
import {environment} from "../../../environments/environment";
import {BehaviorSubject, Observable} from "rxjs";
import {Game} from "../../model/game";
import {Time} from "../../model/time";


@Injectable({
  providedIn: 'root'
})
export class GameSocketService implements OnInit {

  private gameSubject = new BehaviorSubject<Game>(new Game(100, 200));
  private timerSubject = new BehaviorSubject<Time>(new Time(0, 0, 0));

  private gameMessagingService: MessagingService;
  private timerMessagingService: MessagingService;

  constructor() {

    this.gameMessagingService = new MessagingService(environment.WS_PATH + '/game', '/topic/game');
    this.gameMessagingService.stream().subscribe((message: Message) => {
      this.gameSubject.next(new Game(JSON.parse(message.body).smallBlind, JSON.parse(message.body).bigBlind))
    });

    this.timerMessagingService = new MessagingService(environment.WS_PATH + '/game', '/topic/timer');
    this.timerMessagingService.stream().subscribe((message: Message) => {
      this.timerSubject.next(new Time(JSON.parse(message.body).hours,
        JSON.parse(message.body).minutes,
        JSON.parse(message.body).seconds))
    });
  };

  ngOnInit(): void {
  }

  getGame(): Observable<Game> {
    return this.gameSubject.asObservable();
  }

  getTimer(): Observable<Time> {
    return this.timerSubject.asObservable();

  }
}
