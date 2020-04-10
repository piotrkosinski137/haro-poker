package app.domain.player;

import java.util.UUID;

public class GamePlayer {

    private final UUID id;
    private final Integer tableNumber;
    private final String name;
    private int balance;
    private boolean active;

    public GamePlayer(String name, Integer tableNumber) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.tableNumber = tableNumber;
        balance = 10000;
        active = true;
    }

    public UUID getId() {
        return id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    void updateBalance(int amount) {
        balance = amount;
    }

    public String getName() {
        return name;
    }

    void setActive(boolean active) {
        this.active = active;
    }


}
