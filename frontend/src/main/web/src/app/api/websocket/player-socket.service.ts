import {Injectable, OnInit} from '@angular/core';
import {MessagingService} from "./messaging.service";
import {Message} from "@stomp/stompjs";
import {environment} from "../../../environments/environment";
import {BehaviorSubject, Observable} from "rxjs";
import {Player} from "../../model/player";


@Injectable({
  providedIn: 'root'
})
export class PlayerSocketService implements OnInit {

  private playersSubject = new BehaviorSubject<Player[]>([]);
  private messagingService: MessagingService;

  constructor() {
    this.messagingService = new MessagingService(environment.WS_PATH + '/game', '/topic/players');
    this.messagingService.stream().subscribe((message: Message) => {
      console.log("When subscribed, it loads players from backend");
      this.playersSubject.next([...JSON.parse(message.body)])
    });
  };

  ngOnInit(): void {
  }

  getPlayers(): Observable<Player[]> {
    return this.playersSubject.asObservable();
  }

  //    Blueprint how to send messages directly via websocket
  // registerPlayer(playerName: string) {
  //   this.messagingService.send("/players", playerName);
  // }
}
