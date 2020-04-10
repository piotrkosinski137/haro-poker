import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LocalStorageService} from "../local-storage.service";
import {RoundPlayer} from "../../model/round-player";

@Injectable({
  providedIn: 'root'
})
export class RoundPlayerRestService {

  constructor(private http: HttpClient, private localStorageService: LocalStorageService) {
  }

  manualRoundBidsUpdate(players: RoundPlayer[]) {
    // TODO request from admin panel to update roundBids
  }

  bid() {

  }

  fold() {

  }
}
