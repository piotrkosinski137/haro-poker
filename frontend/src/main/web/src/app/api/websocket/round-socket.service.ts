import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Message} from '@stomp/stompjs';
import {Card} from '../../model/card';
import {MessagingService} from './messaging.service';
import {Round} from '../../model/round';
import {RoundStage} from '../../model/round-stage';
import {map} from 'rxjs/operators';
import {GameRestService} from '../rest/game-rest.service';

@Injectable({
  providedIn: 'root'
})
export class RoundSocketService {

  private roundSubject = new BehaviorSubject<Round>(new Round(RoundStage.NOT_STARTED, []));
  private messagingService: MessagingService;
  private playerCardsSubject = new BehaviorSubject([new Card('card_', 'back'), new Card('card_', 'back')]);

  constructor(private gameRestService: GameRestService) {
    this.messagingService = new MessagingService(environment.WS_PATH + '/game', '/topic/round');
    this.messagingService.stream().subscribe((message: Message) => {
      console.log('ROUND CAME:');
      console.log(message.body);

      if (JSON.parse(message.body).stage === 'INIT') {
        this.gameRestService.getPlayerCards().subscribe(cards => {
          this.playerCardsSubject.next(cards);
        });
      }

      this.roundSubject.next(new Round(
        RoundStage[String(JSON.parse(message.body).stage)],
        [...JSON.parse(message.body).cards]
      ));
    });
  }

  getCards(): Observable<Card[]> {
    return this.roundSubject.pipe(map(round => round.cards));
  }

  getPlayerCardsSubject() {
    return this.playerCardsSubject;
  }
}

