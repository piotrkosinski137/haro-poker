export class BalanceRequest {

  constructor(private _id: string, private _balance: string) {
  }

  get id(): string {
    return this._id;
  }

  get balance(): string {
    return this._balance;
  }


  set id(value: string) {
    this._id = value;
  }

  set balance(value: string) {
    this._balance = value;
  }
}
