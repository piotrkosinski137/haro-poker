package app.web.websocket.dto;

import app.domain.game.Blinds;

public class GameDto {
    private int smallBlind;
    private int bigBlind;

    private GameDto() {
    }

    public GameDto(Blinds blinds) {   //TODO refactor to have blinds and timestamp!!!
        smallBlind = blinds.getSmall();
        bigBlind = blinds.getBig();
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
}
