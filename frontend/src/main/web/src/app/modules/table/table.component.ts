import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subscription} from 'rxjs';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {LoginModalComponent} from '../login-modal/login-modal.component';
import {GamePlayer} from '../../model/game-player';
import {Card} from '../../model/card';
import {RoundSocketService} from '../../api/websocket/round-socket.service';
import {GamePlayerSocketService} from '../../api/websocket/game-player-socket.service';
import {GamePlayerRestService} from '../../api/rest/game-player-rest.service';
import {RoundPlayerSocketService} from '../../api/websocket/round-player-socket.service';
import {RoundPlayer} from '../../model/round-player';
import {LocalStorageService} from '../../api/local-storage.service';
import {GameSocketService} from '../../api/websocket/game-socket.service';
import {GameRestService} from '../../api/rest/game-rest.service';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})

export class TableComponent implements OnInit, AfterViewInit, OnDestroy {

  gamePlayerSubscription: Subscription;
  roundPlayerSubscription: Subscription;
  roundSubscription: Subscription;

  gamePlayers: GamePlayer[];
  roundPlayers: RoundPlayer[];
  cards: Card[] = [];
  gameTimestamp$: Observable<number>;

  constructor(private modalService: NgbModal,
              private localStorageService: LocalStorageService,
              private gamePlayerRestService: GamePlayerRestService,
              private gameRestService: GameRestService,
              private gamePlayerSocketService: GamePlayerSocketService,
              private roundPlayerSocketService: RoundPlayerSocketService,
              private roundSocketService: RoundSocketService,
              private gameSocketService: GameSocketService) {
  }

  ngOnInit(): void {
    localStorage.clear();
    this.roundSubscription = this.roundSocketService.getCards().subscribe(cards => this.addNewCards(cards));
    this.gamePlayerSubscription = this.gamePlayerSocketService.getGamePlayers().subscribe(players => this.gamePlayers = players);
    this.roundPlayerSubscription = this.roundPlayerSocketService.getRoundPlayers().subscribe(players => this.roundPlayers = players);
    this.gameTimestamp$ = this.gameSocketService.getGameTimestamp();
  }

  addNewCards(cardsBatch: Card[]) {
    if (cardsBatch.length < this.cards.length) {
      this.cards = cardsBatch;
    } else {
      for (const batchCard of cardsBatch) {
        if (this.cards.find(card => card.suit === batchCard.suit && card.rank === batchCard.rank) === undefined) {
          this.cards.push(batchCard);
        }
      }
    }
  }

  ngAfterViewInit() {
    this.openModal();
  }

  openModal() {
    this.modalService.open(LoginModalComponent, {
      backdrop: 'static',
      keyboard: false
    }).result.then((playerName) => {
      this.gamePlayerRestService.registerGamePlayer(playerName);
    });
  }

  getMaxBet() {
    return this.roundPlayers.map(player => player.turnBid).reduce((prev, curr) => (prev > curr) ? prev : curr);
  }

  getGamePlayerByTableNumber(tableNumber: number) {
    return this.gamePlayers.find(player => player.tableNumber === tableNumber);
  }

  getRoundPlayerByTableNumber(tableNumber: number) {
    return this.roundPlayers.find(player => player.tableNumber === tableNumber);
  }

  calculateRoundPot() {
    return this.roundPlayers.map(player => player.roundBid).reduce((totalRoundBid, roundBid) => totalRoundBid + roundBid, 0);
  }

  calculateTurnPot() {
    return this.roundPlayers.map(player => player.turnBid).reduce((totalTurnBid, turnBid) => totalTurnBid + turnBid, 0);
  }

  isPlayerRound() {
    const player = this.getRoundSessionPlayer();
    return player === undefined ? false : player.hasTurn;
  }

  getRoundSessionPlayer() {
    return this.roundPlayers.find(player => player.id === this.localStorageService.sessionId);
  }

  getGameSessionPlayer() {
    return this.gamePlayers.find(player => player.id === this.localStorageService.sessionId);
  }

  isAdmin() {
    const gamePlayer = this.getGameSessionPlayer();
    if (gamePlayer !== undefined) {
      return gamePlayer.tableNumber === 1;
    }
  }

  onWinnerPicked(id: string) {
    this.gameRestService.finishRound(id);
  }

  ngOnDestroy(): void {
    this.gamePlayerSubscription.unsubscribe();
    this.roundPlayerSubscription.unsubscribe();
    this.roundSubscription.unsubscribe();
  }
}
