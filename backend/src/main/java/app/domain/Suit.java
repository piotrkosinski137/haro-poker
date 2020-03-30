package app.domain;

enum Suit {
  DIAMONDS(1),
  CLUBS(2),
  HEARTS(3),
  SPADES(4);

  private final int value;

  Suit(final int value) {
    this.value = value;
  }

  int getValue() {
    return value;
  }
}
