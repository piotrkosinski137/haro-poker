import {Component, OnDestroy, OnInit} from '@angular/core';
import {PlayerService} from "../../api/player.service";
import {Subscription} from "rxjs";
import {Player} from "../../model/player";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  playerSubscription: Subscription;
  player: Player;

  constructor(private playerService: PlayerService) { }

  ngOnInit() {
  }

  onChangePlayerStateClicked() {
    this.playerSubscription = this.playerService.getSessionPlayer().subscribe(player => this.player = player);
    this.player.isActive = !this.player.isActive;
  }

  ngOnDestroy() {
    this.playerSubscription.unsubscribe()
  }
}
