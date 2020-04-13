import {Card} from './card';

export class RoundPlayer {
  constructor(public id: string,
              public tableNumber: number,
              public balance: number,
              public turnBid: number,
              public roundBid: number,
              public hasTurn: boolean,
              public hasFolded: boolean,
              public playerPosition: string,
              public cardsInHand: Card[]
  ) {
  }
}
