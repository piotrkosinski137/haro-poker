package app.domain.player;

import java.util.UUID;

public class GamePlayer {

    private final UUID id;
    private final Integer tableNumber;
    private final String name;
    private int balance;
    private boolean active;

    GamePlayer(final String name, final Integer tableNumber) {
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
        checkIfPlayerIsActive();
    }

    public String getName() {
        return name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    void deactivatePlayer(){ active = false;}

    void activatePlayer(){ active = true;}

    private void checkIfPlayerIsActive() {
        active = balance > 0;
    }

}
