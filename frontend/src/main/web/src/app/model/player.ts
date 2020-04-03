import {PlayerPosition} from "./player-position";

export class Player {

  constructor(private _id: number,
              private _name: string,
              private _balance: number,
              private _turnBid: number,
              private _hasTurn: boolean,
              private _playerPosition: PlayerPosition,
              private _hasFolded: boolean
  ) {
  }


  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  get turnBid(): number {
    return this._turnBid;
  }

  get hasTurn(): boolean {
    return this._hasTurn;
  }


  get playerPosition(): PlayerPosition {
    return this._playerPosition;
  }

  get balance(): number {
    return this._balance;
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


  get hasFolded(): boolean {
    return this._hasFolded;
  }
}
