package app.domain.player;

public class Player {

    private final int id;
    private final String name;
    private int balance;

    /*
     * id will come from frontend. It knows the best which table number is empty
     * */
    Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void updateBalance(int amount) {
        balance = amount;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isActive() {
        return balance > 0;
    }
}
