import {Injectable, OnInit} from '@angular/core';
import {MessagingService} from "./messaging.service";
import {Message} from "@stomp/stompjs";
import {environment} from "../../../environments/environment";
import {BehaviorSubject, Observable} from "rxjs";
import {Game} from "../../model/game";


@Injectable({
  providedIn: 'root'
})
export class GameSocketService implements OnInit {

  private gameSubject = new BehaviorSubject<Game>(new Game(100, 200));
  private messagingService: MessagingService;

  constructor() {

    this.messagingService = new MessagingService(environment.WS_PATH + '/game', '/topic/game');
    this.messagingService.stream().subscribe((message: Message) => {
      this.gameSubject.next(new Game(JSON.parse(message.body).smallBlind, JSON.parse(message.body).bigBlind))
    });
  };

  ngOnInit(): void {
  }

  getGame(): Observable<Game> {
    return this.gameSubject.asObservable();
  }

}
