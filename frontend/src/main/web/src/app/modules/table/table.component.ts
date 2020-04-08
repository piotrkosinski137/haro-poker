import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {LoginModalComponent} from "../login-modal/login-modal.component";
import {Player} from "../../model/player";
import {PlayerRestService} from "../../api/rest/player-rest.service";
import {Card} from "../../model/card";
import {CardsSocketService} from "../../api/websocket/cards-socket.service";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})

export class TableComponent implements OnInit, AfterViewInit, OnDestroy {

  playersSubscription: Subscription;
  cardSubscription: Subscription;
  players: Player[];
  cards: Card[];

  constructor(private modalService: NgbModal,
              private playerService: PlayerRestService, private cardsService: CardsSocketService) {
  }

  ngOnInit(): void {
    localStorage.clear();
    this.cardSubscription = this.cardsService.getCards().subscribe(cards => this.cards = cards);
    this.playersSubscription = this.playerService.getPlayers().subscribe(players => this.players = players);
  }

  ngAfterViewInit() {
    this.openModal();
  }

  openModal() {
    this.modalService.open(LoginModalComponent, {
      backdrop: 'static',
      keyboard: false
    }).result.then((playerName) => {
      this.playerService.registerPlayer(playerName);
    });
  }

  getPlayerByTableNumber(tableNumber: number) {
    return this.players.find(player => player.tableNumber == tableNumber);
  }

  ngOnDestroy(): void {
    this.playersSubscription.unsubscribe();
    this.cardSubscription.unsubscribe();
  }
}
