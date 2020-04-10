import {MessagingService} from "./messaging.service";
import {Message} from "@stomp/stompjs";
import {environment} from "../../../environments/environment";
import {BehaviorSubject, Observable} from "rxjs";
import {Injectable, OnInit} from "@angular/core";
import {RoundPlayer} from "../../model/round-player";

@Injectable({
  providedIn: 'root'
})
export class RoundPlayerSocketService implements OnInit {

  private roundPlayersSubject = new BehaviorSubject<RoundPlayer[]>([]);
  private messagingService: MessagingService;

  constructor() {
    this.messagingService = new MessagingService(environment.WS_PATH + '/game', '/topic/round-players');
    this.messagingService.stream().subscribe((message: Message) => {
      console.log("When subscribed, it loads roundPlayers from backend");
      this.roundPlayersSubject.next([...JSON.parse(message.body)])
    });
  };

  ngOnInit(): void {
  }

  getRoundPlayers(): Observable<RoundPlayer[]> {
    return this.roundPlayersSubject.asObservable();
  }
}
