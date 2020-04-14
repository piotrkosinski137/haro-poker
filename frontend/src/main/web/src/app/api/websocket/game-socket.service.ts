import {Injectable} from '@angular/core';
import {MessagingService} from './messaging.service';
import {Message} from '@stomp/stompjs';
import {environment} from '../../../environments/environment';
import {BehaviorSubject, Observable} from 'rxjs';
import {Game} from '../../model/game';
import {map} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class GameSocketService {

  private gameSubject = new BehaviorSubject<Game>(new Game(100, 200, 0));

  private gameMessagingService: MessagingService;

  constructor() {
    this.gameMessagingService = new MessagingService(environment.WS_PATH + '/game', '/topic/game');
    this.gameMessagingService.stream().subscribe((message: Message) => {
      console.log('GAME CAME:');
      console.log(message.body);

      this.gameSubject.next(new Game(
        JSON.parse(message.body).smallBlind,
        JSON.parse(message.body).bigBlind,
        JSON.parse(message.body).gameTimeStamp));
    });
  }

  getGame(): Observable<Game> {
    return this.gameSubject.asObservable();
  }

  getGameTimestamp() {
    return this.getGame().pipe(map(game => game.gameTimeStamp));
  }
}
