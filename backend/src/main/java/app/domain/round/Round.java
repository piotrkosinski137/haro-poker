package app.domain.round;

import static app.domain.round.RoundStage.FLOP;
import static app.domain.round.RoundStage.INIT;
import static app.domain.round.RoundStage.RIVER;
import static app.domain.round.RoundStage.TURN;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import app.domain.card.Card;
import app.domain.roundPlayer.RoundPlayer;

class Round {

  private Set<Card> tableCards;
  private Stack<RoundPlayer> roundPlayers;
  private RoundStage stage;
  private int totalPot;  //we will see


  public Round() {
    tableCards = new HashSet<>();
    stage = RoundStage.INIT;
  }

  //nextPlayer

  public Set<Card> getTableCards() {
    return tableCards;
  }

  public int getPot() {
    return totalPot;
  }

  public RoundStage getRoundStage() {
    return stage;
  }

  public void changeRoundStage() {
    switch (stage) {
      case INIT:
        stage = FLOP;
        break;
      case FLOP:
        stage = TURN;
        break;
      case TURN:
        stage = RIVER;
        break;
      case RIVER:
        stage = INIT; // to consider
        break;
      default:
        break;
    }
  }
}
