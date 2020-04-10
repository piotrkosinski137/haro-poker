import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {LocalStorageService} from "../local-storage.service";
import {RoundPlayer} from "../../model/round-player";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RoundPlayerRestService {

  constructor(private http: HttpClient, private localStorageService: LocalStorageService) {
  }

  manualRoundBidsUpdate(players: RoundPlayer[]) {
    // TODO request from admin panel to update roundBids
  }

  bid(amount: number) {
    let params = new HttpParams().set('amount', String(amount));
    return this.http.post(environment.PROXY_PATH + "players/" + this.localStorageService.sessionId + "/bid",
      null, {params: params}).subscribe();
  }

  fold() {
    return this.http.post(environment.PROXY_PATH + "players/" + this.localStorageService.sessionId + "/fold",
      null).subscribe();
  }
}
