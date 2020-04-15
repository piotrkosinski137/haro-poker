import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {GamePlayer} from '../../../model/game-player';
import {RoundPlayer} from '../../../model/round-player';
import {LocalStorageService} from '../../../api/local-storage.service';
import {RoundSocketService} from '../../../api/websocket/round-socket.service';
import {Card} from '../../../model/card';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-player-game-dashboard',
  templateUrl: './player-game-dashboard.component.html',
  styleUrls: ['./player-game-dashboard.component.scss']
})
export class PlayerGameDashboardComponent implements OnInit, OnDestroy {

  @Input()
  gamePlayer: GamePlayer;
  @Input()
  roundPlayer: RoundPlayer;
  @Input()
  isAdmin: boolean;
  playerCards: Card[] = [];
  @Output()
  winnerPicked = new EventEmitter<string>();

  cardsSubscription: Subscription;

  constructor(private localStorageService: LocalStorageService, private roundSocketService: RoundSocketService) {
  }

  ngOnInit() {
    this.cardsSubscription = this.roundSocketService.getPlayerCardsSubject().subscribe(cards => {
      if (!this.cardsAreTheSame(cards) || this.playerCards.length === 0) {
        this.playerCards = cards;
      }
    });
  }

  cardsAreTheSame(cards: Card[]) {
    if ((this.areTheSame(this.playerCards[0], cards[0]) && this.areTheSame(this.playerCards[1], cards[1])) ||
      (this.areTheSame(this.playerCards[1], cards[0]) && this.areTheSame(this.playerCards[0], cards[1]))) {
      return true;
    }
  }

  areTheSame(card1: Card, card2: Card) {
    return JSON.stringify(card1) === JSON.stringify(card2);
  }

  isSessionPlayer() {
    return this.localStorageService.isSessionPlayer(this.gamePlayer.id);
  }

  isActive() {
    return this.roundPlayer !== undefined;
  }

  onPickWinnerClick(id: string) {
    this.winnerPicked.emit(id);
  }

  containsCards() {
    if (this.isActive()) {
      return this.roundPlayer.cardsInHand.length > 0;
    }
  }

  ngOnDestroy(): void {
    this.cardsSubscription.unsubscribe();
  }
}
