package app.domain.round;

enum RoundStage {
    NOT_STARTED(0), INIT(0), FLOP(3), TURN(1), RIVER(1);

    private final int cardAmount;

    RoundStage(final int cardAmount) {
        this.cardAmount = cardAmount;
    }

    int getCardAmount() {
        return cardAmount;
    }
}
