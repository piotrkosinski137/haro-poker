import { Injectable } from '@angular/core';
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  sessionPlayerId = new Subject<string>();

  set sessionId(id: string) {
    localStorage.setItem('playerId', id);
    this.sessionPlayerId.next(id);
  }

  get sessionId() {
    return localStorage.getItem('playerId');
  }

  isSessionPlayer(id: string) {
    return id === this.sessionId;
  }
}
