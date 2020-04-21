package app.domain.round;

import app.domain.card.CardDto;
import java.util.Set;

public class RoundPlayerDto {
    private String id;
    private Integer tableNumber;
    private Set<CardDto> cardsInHand;
    private int balance;
    private int turnBid;
    private int roundBid;
    private boolean hasTurn;
    private boolean hasFolded;
    private String playerPosition;

    private RoundPlayerDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Set<CardDto> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(Set<CardDto> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTurnBid() {
        return turnBid;
    }

    public void setTurnBid(int turnBid) {
        this.turnBid = turnBid;
    }

    public int getRoundBid() {
        return roundBid;
    }

    public void setRoundBid(int roundBid) {
        this.roundBid = roundBid;
    }

    public boolean isHasTurn() {
        return hasTurn;
    }

    public void setHasTurn(boolean hasTurn) {
        this.hasTurn = hasTurn;
    }

    public boolean isHasFolded() {
        return hasFolded;
    }

    public void setHasFolded(boolean hasFolded) {
        this.hasFolded = hasFolded;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }
}
