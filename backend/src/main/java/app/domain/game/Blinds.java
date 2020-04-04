package app.domain.game;

public class Blinds {

    private int small;
    private int big;

    public Blinds() {
        small = 100;
        big = 200;
    }

    public int getSmall() {
        return small;
    }

    public int getBig() {
        return big;
    }

    public void setBlinds(int small, int big) {
        this.small = small;
        this.big = big;
    }
}
