import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) {
  }

  startGame() {
    return this.http.post(environment.PROXY_PATH + "game/start", null).subscribe();
  }

  updateBlinds(small: number) {
    let params = new HttpParams().set('small', String(small));
    return this.http.put(environment.PROXY_PATH + "game/blinds/update", null,
      {params: params}).subscribe();
  }
}
