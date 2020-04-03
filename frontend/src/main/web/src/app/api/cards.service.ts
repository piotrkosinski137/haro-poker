import {Injectable} from '@angular/core';
import {from, Observable, of, timer} from "rxjs";
import {concatMap, map, scan} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class CardsService {

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
  }

  getPlayerCards(id: number): Observable<string[]> {
    return of(this.playerCards[id]);
  }

  getCards(): Observable<string[]> {
    return from(['2C', '6H', '8C', 'QS', 'QH'])
    .pipe(
      timed());
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

