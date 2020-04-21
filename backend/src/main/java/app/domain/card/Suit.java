package app.domain.card;

enum Suit {
    DIAMONDS("D"),
    CLUBS("C"),
    HEARTS("H"),
    SPADES("S");

    private final String value;

    Suit(final String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }
}
