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
    private Set<Card> cardsInHand;
    private int turnBid;
    private int roundBid;
    private boolean hasFolded;
    private Position playerPosition;
    private boolean hasTurn;
    private final Integer tableNumber;
    private boolean madeMoveInStage;

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

    void chargeBlind(final int blind) {
        turnBid += blind;
        roundBid += blind;
        balance -= blind;
    }

    void bid(final int bid) {
        turnBid += bid;
        roundBid += bid;
        balance -= bid;
        hasTurn = false;
        madeMoveInStage = true;
    }

    void allIn() {
        turnBid += balance;
        roundBid += balance;
        balance = 0;
        hasTurn = false;
        madeMoveInStage = true;
    }

    void fold() {
        hasFolded = true;
        hasTurn = false;
        cardsInHand = new HashSet<>();
        madeMoveInStage = true;
    }

    void prepareForNextStage() {
        if (balance > 0 && !hasFolded) {
            madeMoveInStage = false;
        }
        turnBid = 0;
    }

    public void clearBids() {
        turnBid = 0;
        roundBid = 0;
    }

    void winMoney(final int money) {
        balance += money;
    }

    boolean isInGame() {
        return !hasFolded && balance > 0;
    }

    boolean hasAllIn() {
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

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public boolean madeMoveInStage() {
        return madeMoveInStage;
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

    public void setMadeMoveInStage(boolean madeMoveInStage) {
        this.madeMoveInStage = madeMoveInStage;
    }
}
