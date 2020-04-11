package app.domain.round;

import app.domain.card.Card;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class RoundPlayer {

    private final UUID id;
    private int balance;
    private final Set<Card> cardsInHand;
    private int turnBid;
    private int roundBid;
    private boolean outOfBidding;
    private Position playerPosition;
    private boolean hasTurn;
    private final Integer tableNumber;

    RoundPlayer(final UUID id, final int balance, final Integer tableNumber) {
        cardsInHand = new HashSet<>();
        this.id = id;
        this.balance = balance;
        playerPosition = Position.NONE;
        this.tableNumber = tableNumber;
    }

    public UUID getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public Set<Card> getCardsInHand() {
        return Collections.unmodifiableSet(cardsInHand);
    }

    void putCardsInHand(final Set<Card> cards) {
        cardsInHand.addAll(cards);
    }

    void setPlayerPosition(Position position) {
        this.playerPosition = position;
    }

    void bid(final int bid) {
        turnBid += bid;
        roundBid += bid;
        balance -= bid;
        hasTurn = false;
    }

    void allIn() {
        turnBid += balance;
        roundBid += balance;
        balance = 0;
        hasTurn = false;
        outOfBidding = true;
    }

    void fold() {
        outOfBidding = true;
        hasTurn = false;
    }

    void prepareForNextStage() {
        turnBid = 0;
    }

    void winMoney(final int money) {
        balance += money;
    }

    boolean isInGame() {
        return !outOfBidding;
    }

    boolean hasNoFunds() {
        return balance == 0;
    }

    int getTurnBid() {
        return turnBid;
    }

    int getRoundBid() {
        return roundBid;
    }

    void setHasTurn(boolean hasTurn) {
        this.hasTurn = hasTurn;
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

    public void hasTurn(boolean hasTurn) {
        this.hasTurn = hasTurn;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }
}
