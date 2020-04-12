import {RoundStage} from './round-stage';
import {Card} from './card';

export class Round {
  constructor(public stage: RoundStage, public cards: Card[]) {
  }
}
