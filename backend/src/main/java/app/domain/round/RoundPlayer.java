package app.domain.round;

import app.domain.card.Card;

import java.util.Objects;
import java.util.Set;

public class RoundPlayer {

    private int id;
    private int balance;
    private Set<Card> cardsInHand;
    // sum of bids during one stage (Eg. flop, river)
    private int turnBid;
    // sum of bids during whole round, info needed for manual cases (when there will be couple all'ins)
    private int roundBid;
    private boolean hasFolded;

    private RoundPlayer() {
    }

    public RoundPlayer(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    void putCardsInHand(Set<Card> cards) {
        cardsInHand.addAll(cards);
    }

    void bid(int bid) {
        turnBid += bid;
        roundBid += bid;
        balance -= bid;
    }

    void fold() {
        hasFolded = true;
    }

    void nextStage() {
        turnBid = 0;
    }

    void nextRound() {
        turnBid = 0;
        roundBid = 0;
        hasFolded = false;
    }

    void winMoney(int money) {
        balance += money;
    }

    boolean isInGame() {
        return !hasFolded;
    }

    int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    int getTurnBid() {
        return turnBid;
    }

    int getRoundBid() {
        return roundBid;
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
