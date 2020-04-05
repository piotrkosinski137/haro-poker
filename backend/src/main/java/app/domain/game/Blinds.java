package app.domain.game;

public class Blinds {

    private int small;
    private int big;

    Blinds() {
        small = 100;
        big = 200;
    }

    public int getSmall() {
        return small;
    }

    public int getBig() {
        return big;
    }

    void setBlinds(int blind) {
        this.small = blind/2;
        this.big = blind;
    }
}
