package app.domain.card;

public class Card {

  private final Rank rank;
  private final Suit suit;

  Card(final Rank rank, final Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }
}
