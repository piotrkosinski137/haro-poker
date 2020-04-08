import {Injectable, OnInit} from '@angular/core';
import {Player} from "../model/player";
import {Observable, of} from "rxjs";
import {MessagingService} from "./messaging.service";
import {Message} from "@stomp/stompjs";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class PlayerService implements OnInit {

  private messagingService: MessagingService;
  players = [];

  constructor() {
    this.messagingService = new MessagingService('wss://' + environment.WS_PATH + '/players/add', '/topic/players');

    this.messagingService.stream().subscribe((message: Message) => {
      console.log("When subscribed, it loads players from backend");
      this.addPlayers(message.body)
    });
  };

  ngOnInit(): void {
  }

  getPlayers(): Observable<Player[]> {
    return of(this.players);
  }

  updateBalances(players: Player[]) {
    this.players = players
  }

  getSessionPlayer() {
    return of(this.players.find(player => {
      return player.id.toString() == localStorage.getItem("playerId")
    }))
  }

  addPlayers(players: any) {
    this.players.push(...JSON.parse(players));
  }

  registerPlayer(playerName: string) {
    this.messagingService.send("/players/add", playerName);
  }
}
