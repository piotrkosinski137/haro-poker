import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {LoginModalComponent} from "../login-modal/login-modal.component";
import {GamePlayer} from "../../model/game-player";
import {Card} from "../../model/card";
import {CardsSocketService} from "../../api/websocket/cards-socket.service";
import {GamePlayerSocketService} from "../../api/websocket/game-player-socket.service";
import {GamePlayerRestService} from "../../api/rest/game-player-rest.service";
import {RoundPlayerSocketService} from "../../api/websocket/round-player-socket.service";
import {RoundPlayer} from "../../model/round-player";
import {LocalStorageService} from "../../api/local-storage.service";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})

export class TableComponent implements OnInit, AfterViewInit, OnDestroy {

  gamePlayerSubscription: Subscription;
  roundPlayerSubscription: Subscription;
  cardSubscription: Subscription;

  gamePlayers: GamePlayer[];
  roundPlayers: RoundPlayer[];
  cards: Card[];

  constructor(private modalService: NgbModal,
              private gamePlayerRestService: GamePlayerRestService,
              private gamePlayerSocketService: GamePlayerSocketService,
              private roundPlayerSocketService: RoundPlayerSocketService,
              private localStorageService: LocalStorageService,
              private cardsService: CardsSocketService) {
  }

  ngOnInit(): void {
    localStorage.clear();
    this.cardSubscription = this.cardsService.getCards().subscribe(cards => this.cards = cards);
    this.gamePlayerSubscription = this.gamePlayerSocketService.getGamePlayers().subscribe(players => this.gamePlayers = players);
    this.roundPlayerSubscription = this.roundPlayerSocketService.getRoundPlayers().subscribe(players => this.roundPlayers = players);
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

  getGamePlayerByTableNumber(tableNumber: number) {
    return this.gamePlayers.find(player => player.tableNumber == tableNumber);
  }

  getRoundPlayerByTableNumber(tableNumber: number) {
    return this.roundPlayers.find(player => player.tableNumber == tableNumber);
  }

  calculateRoundPot() {
    return this.roundPlayers.map(player => player.roundBid).reduce(function(totalRoundBid, roundBid){
      return totalRoundBid + roundBid;
    },0);
  }

  ngOnDestroy(): void {
    this.gamePlayerSubscription.unsubscribe();
    this.roundPlayerSubscription.unsubscribe();
    this.cardSubscription.unsubscribe();
  }

  isPlayerRound() {
    let player = this.roundPlayers.find(player => player.id === this.localStorageService.sessionId);
    return player === undefined ? false : player.hasTurn;
  }
}
