export class GamePlayer {

  constructor(public id: string,
              public tableNumber: number,
              public name: string,
              public active: boolean,
              public balance: number
  ) {
  }
}
