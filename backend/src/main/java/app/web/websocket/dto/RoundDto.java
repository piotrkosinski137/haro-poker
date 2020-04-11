package app.web.websocket.dto;

import java.util.ArrayList;
import java.util.List;

public class RoundDto {
    private List<CardDto> cards;
    private String stage;

    private RoundDto() {
        cards = new ArrayList<>();
    }

    public List<CardDto> getCards() {
        return cards;
    }

    public void setCards(List<CardDto> cards) {
        this.cards = cards;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
