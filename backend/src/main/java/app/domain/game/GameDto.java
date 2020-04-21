package app.domain.game;

public class GameDto {
    private int smallBlind;
    private int bigBlind;
    private long gameTimeStamp;

    GameDto(Blinds blinds, long gameTimeStamp) {
        this.gameTimeStamp = gameTimeStamp;
        this.smallBlind = blinds.getSmall();
        this.bigBlind = blinds.getBig();
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public void setSmallBlind(int smallBlind) {
        this.smallBlind = smallBlind;
    } //todo setters can be removed?

    public int getBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
    }

    public long getGameTimeStamp() {
        return gameTimeStamp;
    }

    public void setGameTimeStamp(long gameTimeStamp) {
        this.gameTimeStamp = gameTimeStamp;
    }
}
