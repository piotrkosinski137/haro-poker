import {PlayerPosition} from './player-position';

export class GamePlayer {

  constructor(public id: string,
              public tableNumber: number,
              public name: string,
              public playerPosition: PlayerPosition,
              public active: boolean,
  ) {
  }

  isDealer() {
    return this.playerPosition === PlayerPosition.DEALER;
  }

  hasSmallBlind() {
    return this.playerPosition === PlayerPosition.SMALL_BLIND;
  }

  hasBigBlind() {
    return this.playerPosition === PlayerPosition.BIG_BLIND;
  }
}
