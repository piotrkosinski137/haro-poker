package app.domain.round;

import app.domain.card.Card;
import java.util.Objects;
import java.util.Set;

public class RoundPlayer {

  private int id;
  private int balance;
  private Set<Card> cardsInHand;
  private int turnBid;
  private int roundBid;
  private boolean hasFolded;

  public RoundPlayer(int id, int balance) {
    this.id = id;
    this.balance = balance;
  }

  public void bid(int bid) {
    turnBid += bid;
  }

  public int fold() {
    hasFolded = true;
    return turnBid;
  }

  public void nextRound() {
    roundBid += turnBid;
    turnBid = 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoundPlayer that = (RoundPlayer) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
