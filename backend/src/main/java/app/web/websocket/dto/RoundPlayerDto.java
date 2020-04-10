package app.web.websocket.dto;

public class RoundPlayerDto {
    private String id;
    private Integer tableNumber;
    private int balance;
    private int turnBid;
    private int roundBid;
    private boolean hasTurn;
    private boolean hasFolded;

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

}
