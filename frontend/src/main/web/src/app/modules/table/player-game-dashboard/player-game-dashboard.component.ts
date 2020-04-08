import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Player} from "../../../model/player";
import {Subscription} from "rxjs";
import {CardsSocketService} from "../../../api/websocket/cards-socket.service";

@Component({
  selector: 'app-player-game-dashboard',
  templateUrl: './player-game-dashboard.component.html',
  styleUrls: ['./player-game-dashboard.component.scss']
})
export class PlayerGameDashboardComponent implements OnInit, OnDestroy {

  @Input()
  player: Player;
  cards: string[];
  playerCardsSubscription: Subscription;

  constructor(private cardsService: CardsSocketService) { }

  ngOnInit() {
    if(this.isSessionPlayer()) {
      this.playerCardsSubscription = this.cardsService.getPlayerCards(this.player.tableNumber)
      .subscribe(cards => this.cards = cards);
    }
  }

  hasTurn() {
    return this.player.hasTurn;
  }

  isSessionPlayer() {
    return this.player.tableNumber === +localStorage.getItem('playerId');
  }

  hasFolded() {
    return this.player.hasFolded;
  }

  isActive() {
    return this.player.active;
  }

  ngOnDestroy() {
    this.playerCardsSubscription.unsubscribe();
  }
}
