import {MessagingService} from './messaging.service';
import {Message} from '@stomp/stompjs';
import {environment} from '../../../environments/environment';
import {BehaviorSubject, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {RoundPlayer} from '../../model/round-player';

@Injectable({
  providedIn: 'root'
})
export class RoundPlayerSocketService {

  private roundPlayersSubject = new BehaviorSubject<RoundPlayer[]>([]);
  private messagingService: MessagingService;

  constructor() {
    this.messagingService = new MessagingService(environment.WS_PATH + '/game', '/topic/round-players');
    this.messagingService.stream().subscribe((message: Message) => {
      console.log('ROUND_PLAYERS CAME:');
      console.log(message.body);
      this.roundPlayersSubject.next([...JSON.parse(message.body)]);
    });
  }

  getRoundPlayers(): Observable<RoundPlayer[]> {
    return this.roundPlayersSubject.asObservable();
  }

  /*  getSessionPlayer(): Observable<RoundPlayer> {
      return this.roundPlayersSubject.asObservable()
        .pipe(map(players => players.find(player => player.id = this.localStorageService.sessionId)));
    }*/
}
