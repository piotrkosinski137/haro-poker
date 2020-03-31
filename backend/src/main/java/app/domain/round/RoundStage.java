package app.domain.round;

enum RoundStage {
  INIT(0), FLOP(3), TURN(1), RIVER(1);

  private final int cardAmount;

  RoundStage(final int cardAmount) {
    this.cardAmount = cardAmount;
  }

  int getCardAmount() {
    return cardAmount;
  }
}
