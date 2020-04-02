import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {from, Observable, timer} from "rxjs";
import {environment} from "../../environments/environment";
import {concatMap, map, scan} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class HelloService {

  constructor(private http: HttpClient) {
  }

  getGreeting(): Observable<any> {
    return this.http.get(environment.PROXY_PATH + 'hello');
  }

  getCards(): Observable<string[]> {
    return from(['2C', '6H', '8C', 'QS', 'QH'])
    .pipe(
      timed(1000));
  }
}

const timeOf = (interval: number) => <T>(val: T) =>
  timer(interval).pipe(map(x => val));

const timed = (interval: number) => <T>(source: Observable<T>) =>
  source.pipe(
    concatMap(timeOf(500)),
    map(x => [x]),
    scan((acc, val) => [...acc, ...val]),
  );
