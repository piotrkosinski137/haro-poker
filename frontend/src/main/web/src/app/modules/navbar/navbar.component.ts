import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Player} from "../../model/player";
import {PlayerRestService} from "../../api/rest/player-rest.service";
import {LocalStorageService} from "../../api/local-storage.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  player: Player;

  constructor(private playerService: PlayerRestService, private localStorageService: LocalStorageService) {
  }

  ngOnInit() {
    this.subscription = this.localStorageService.sessionPlayerId.subscribe(sessionId =>
      this.playerService.getSessionPlayer().subscribe(player => this.player = player));
  }

  onChangePlayerStateClicked() {
    this.player.active = !this.player.active;
  }

  //TODO
  onGameStart() {
  }

  isAdmin(tableNumer: number) {
    return tableNumer === 1;
  }

  ngOnDestroy() {
    this.subscription.unsubscribe()
  }
}
