package app.domain.player;

public class Player {

    private final int id;
    private final String name;
    private int balance;
    private boolean isActive;

    /**
     * Id will come from frontend. It knows the best which table number is empty
     * */
    Player(int id, String name) {
        this.id = id;
        this.name = name;
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
    }

    public String getName() {
        return name;
    }

    void setActive(boolean active) {
        isActive = active;
    }


}
