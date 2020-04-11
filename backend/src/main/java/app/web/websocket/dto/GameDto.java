package app.web.websocket.dto;

import app.domain.game.Blinds;

public class GameDto {
    private int smallBlind;
    private int bigBlind;
    private long timeStamp;

    private GameDto() { //???
    }

    public GameDto(Blinds blinds, long timeStamp) {
        this.timeStamp = timeStamp;
        this.smallBlind = blinds.getSmall();
        this.bigBlind = blinds.getBig();
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public void setSmallBlind(int smallBlind) {
        this.smallBlind = smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
