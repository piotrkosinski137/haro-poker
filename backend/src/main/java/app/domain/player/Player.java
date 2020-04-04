package app.domain.player;

public class Player {

    private final int id;
    private final String name;
    private int balance;
    private boolean isActive;

    Player(String name) {
        id = generateId();
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    private int generateId() {
        // need to know every player id
        return 1;
    }

    public boolean isActive() {
        return isActive;
    }
}
