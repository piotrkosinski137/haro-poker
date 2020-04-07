package app.domain.player;

public class Player {

    private final int id;
    //TODO player token/uuid/spring security user
    private final String name;
    private int balance;
    private boolean isActive;

    /**
     * Id will come from frontend. It knows the best which table number is empty
     * */
    Player(final int id, final String name) {
        this.id = id;
        this.name = name;
        balance = 1000;
        isActive = true;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isActive() {
        return isActive;
    }

    void updateBalance(int amount) {
        balance = amount;
        checkIfPlayerIsActive();
    }

    public String getName() {
        return name;
    }

    void deactivatePlayer(){ isActive = false;}

    private void checkIfPlayerIsActive() {
        isActive = balance > 0;
    }
}
