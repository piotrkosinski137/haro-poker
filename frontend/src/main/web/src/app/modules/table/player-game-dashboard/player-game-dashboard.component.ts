import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Player} from "../../../model/player";
import {CardsService} from "../../../api/cards.service";
import {Subscription} from "rxjs";

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

  constructor(private cardsService: CardsService) { }

  ngOnInit() {
    if(this.isSessionPlayer()) {
      this.playerCardsSubscription = this.cardsService.getPlayerCards(this.player.id)
      .subscribe(cards => this.cards = cards);
    }
  }

  hasTurn() {
    return this.player.hasTurn;
  }

  isSessionPlayer() {
    return this.player.id === +localStorage.getItem('playerId');
  }

  hasFolded() {
    return this.player.hasFolded;
  }

  isActive() {
    return this.player.isActive;
  }

  ngOnDestroy() {
    this.playerCardsSubscription.unsubscribe();
  }
}
