import {PlayerPosition} from "./player-position";

export class Player {

  constructor(public id: number,
              public name: string,
              public balance: number,
              public turnBid: number,
              public roundBid: number,
              public hasTurn: boolean,
              public playerPosition: PlayerPosition,
              public hasFolded: boolean,
              public active: boolean,
  ) {
  }

  isDealer() {
    return this.playerPosition === PlayerPosition.DEALER
  }

  hasSmallBlind() {
    return this.playerPosition === PlayerPosition.SMALL_BLIND
  }

  hasBigBlind() {
    return this.playerPosition === PlayerPosition.BIG_BLIND
  }
}
