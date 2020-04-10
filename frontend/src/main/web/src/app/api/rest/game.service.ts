import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) {
  }

  startGame() {
    return this.http.post(environment.PROXY_PATH + "game/start", null).subscribe();
  }
}
