package app.domain.round;

import app.domain.card.Card;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RoundPlayer {

    private int id;
    private int balance;
    private final Set<Card> cardsInHand;
    /** sum of bids during one stage (Eg. flop, river) */
    private int turnBid;
    /** sum of bids during whole round, info needed for manual cases (when there will be couple all'ins) */
    private int roundBid;
    private boolean hasFolded;

    RoundPlayer(final int id, final int balance) {
        cardsInHand = new HashSet<>();
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
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

    void bid(final int bid) {
        turnBid += bid;
        roundBid += bid;
        balance -= bid;
    }

    void fold() {
        hasFolded = true;
    }

    void prepareForNextStage() {
        turnBid = 0;
    }

    void winMoney(final int money) {
        balance += money;
    }

    boolean isInGame() {
        return !hasFolded;
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
