import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, of, timer} from "rxjs";
import {concatMap, map, scan} from "rxjs/operators";
import {environment} from "../../../environments/environment";
import {Message} from "@stomp/stompjs";
import {Card} from "../../model/card";
import {MessagingService} from "./messaging.service";

@Injectable({
  providedIn: 'root'
})
export class CardsSocketService {

  private cardsSubject = new BehaviorSubject<Card[]>([]);
  private messagingService: MessagingService;
  playerCards = {
    1: ['AS', 'AH'],
    2: ['3D', 'QH'],
    3: ['4C', '6H'],
    4: ['5C', '6H'],
    5: ['6C', 'QH'],
    6: ['7C', '2H'],
    7: ['8C', 'QH']
  };

  constructor() {
    this.messagingService = new MessagingService(environment.WS_PATH + '/game', '/topic/cards');
    this.messagingService.stream().subscribe((message: Message) => {
      console.log("When subscribed, it loads tableCards from backend");
      this.cardsSubject.next([...JSON.parse(message.body)])
    });
  }

  //TODO
  getPlayerCards(id: number): Observable<string[]> {
    return of(this.playerCards[id]);
  }

  getCards(): Observable<Card[]> {
    return this.cardsSubject.asObservable();
    // .pipe(timed());
  }
}

const timeOf = (interval: number) => <T>(val: T) =>
  timer(interval).pipe(map(() => val));

const timed = () => <T>(source: Observable<T>) =>
  source.pipe(
    concatMap(timeOf(500)),
    map(x => [x]),
    scan((acc, val) => [...acc, ...val]),
  );

